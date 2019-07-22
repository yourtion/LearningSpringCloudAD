package com.yourtion.springcloud.ad.search.impl;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yourtion.springcloud.ad.index.CommonStatus;
import com.yourtion.springcloud.ad.index.DataTable;
import com.yourtion.springcloud.ad.index.adunit.AdUnitIndex;
import com.yourtion.springcloud.ad.index.adunit.AdUnitObject;
import com.yourtion.springcloud.ad.index.creative.CreativeIndex;
import com.yourtion.springcloud.ad.index.creative.CreativeObject;
import com.yourtion.springcloud.ad.index.creativeunit.CreativeUnitIndex;
import com.yourtion.springcloud.ad.index.district.UnitDistrictIndex;
import com.yourtion.springcloud.ad.index.interest.UnitItIndex;
import com.yourtion.springcloud.ad.index.keyword.UnitKeywordIndex;
import com.yourtion.springcloud.ad.search.ISearch;
import com.yourtion.springcloud.ad.search.vo.SearchRequest;
import com.yourtion.springcloud.ad.search.vo.SearchResponse;
import com.yourtion.springcloud.ad.search.vo.feature.DistrictFeature;
import com.yourtion.springcloud.ad.search.vo.feature.FeatureRelation;
import com.yourtion.springcloud.ad.search.vo.feature.ItFeature;
import com.yourtion.springcloud.ad.search.vo.feature.KeywordFeature;
import com.yourtion.springcloud.ad.search.vo.media.AdSlot;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author yourtion
 */
@Slf4j
@Service
public class SearchImpl implements ISearch {

    public SearchResponse fallback(SearchRequest request, Throwable e) {
        return null;
    }

    @Override
    // 比较少使用HystrixCommand，因为需要在线程池中执行，效率低
    @HystrixCommand(fallbackMethod = "fallback")
    public SearchResponse fetchAds(SearchRequest request) {

        // 请求的广告位信息
        var adSlots = request.getRequestInfo().getAdSlots();
        // 三个 Feature
        var keywordFeature = request.getFeatureInfo().getKeywordFeature();
        var districtFeature = request.getFeatureInfo().getDistrictFeature();
        var itFeature = request.getFeatureInfo().getItFeature();
        var relation = request.getFeatureInfo().getRelation();

        // 构造响应对象
        var response = new SearchResponse();
        var adSlot2Ads = response.getAdSlot2Ads();

        for (AdSlot slot : adSlots) {
            Set<Long> targetUnitIdSet;

            // 根据流量类型获取初始 AdUnit
            var adUnitIds = DataTable.of(AdUnitIndex.class).match(slot.getPositionType());

            if (relation == FeatureRelation.AND) {

                filterKeywordFeature(adUnitIds, keywordFeature);
                filterDistrictFeature(adUnitIds, districtFeature);
                filterItFeature(adUnitIds, itFeature);

                targetUnitIdSet = adUnitIds;
            } else {
                targetUnitIdSet = getOrRelation(adUnitIds, keywordFeature, districtFeature, itFeature);
            }

            var unitObjects = DataTable.of(AdUnitIndex.class).fetch(targetUnitIdSet);
            filterAdUnitAndPlanStstus(unitObjects, CommonStatus.VALID);

            var adIds = DataTable.of(CreativeUnitIndex.class).selectAds(unitObjects);
            var creatives = DataTable.of(CreativeIndex.class).fetch(adIds);

            // 通过 AdSlot 实现对 CreativeObject 的过滤
            filterCreativeByAdSlot(creatives, slot.getWidth(), slot.getHeight(), slot.getType());

            adSlot2Ads.put(slot.getAdSlotCode(), buildCreativeResponse(creatives));
        }

        log.info("fetchAds: {} - {}", JSON.toJSONString(request), JSON.toJSONString(response));
        return response;
    }

    private Set<Long> getOrRelation(Set<Long> adUnitIdSet, KeywordFeature keywordFeature, DistrictFeature districtFeature, ItFeature itFeature) {
        if (CollectionUtils.isEmpty(adUnitIdSet)) {
            return Collections.emptySet();
        }
        Set<Long> keywordUnitIdSet = new HashSet<>(adUnitIdSet);
        Set<Long> districtUnitIdSet = new HashSet<>(adUnitIdSet);
        Set<Long> itUnitIdSet = new HashSet<>(adUnitIdSet);

        filterKeywordFeature(keywordUnitIdSet, keywordFeature);
        filterDistrictFeature(districtUnitIdSet, districtFeature);
        filterItFeature(itUnitIdSet, itFeature);

        return new HashSet<>(
                CollectionUtils.union(
                        CollectionUtils.union(keywordUnitIdSet, districtUnitIdSet),
                        itUnitIdSet
                )
        );
    }

    private void filterKeywordFeature(Collection<Long> adUnitIds, KeywordFeature keywordFeature) {
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }
        if (CollectionUtils.isNotEmpty(keywordFeature.getKeywords())) {
            CollectionUtils.filter(adUnitIds,
                    adUnitId -> DataTable.of(UnitKeywordIndex.class).match(adUnitId, keywordFeature.getKeywords()));
        }
    }

    private void filterDistrictFeature(Collection<Long> adUnitIds, DistrictFeature districtFeature) {
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }
        if (CollectionUtils.isNotEmpty(districtFeature.getDistricts())) {
            CollectionUtils.filter(adUnitIds,
                    adUnitId -> DataTable.of(UnitDistrictIndex.class).match(adUnitId, districtFeature.getDistricts()));
        }
    }

    private void filterItFeature(Collection<Long> adUnitIds, ItFeature itFeature) {
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }
        if (CollectionUtils.isNotEmpty(itFeature.getIts())) {
            CollectionUtils.filter(adUnitIds,
                    adUnitId -> DataTable.of(UnitItIndex.class).match(adUnitId, itFeature.getIts()));
        }
    }

    private void filterAdUnitAndPlanStstus(List<AdUnitObject> unitObjects, CommonStatus status) {
        if (CollectionUtils.isEmpty(unitObjects)) {
            return;
        }
        CollectionUtils.filter(unitObjects,
                object -> object.getUnitStatus().equals(status.getStatus())
                        && object.getAdPlanObject().getPlanStatus().equals(status.getStatus())
        );
    }

    private void filterCreativeByAdSlot(List<CreativeObject> creatives, Integer width, Integer height, List<Integer> type) {
        if (CollectionUtils.isEmpty(creatives)) {
            return;
        }

        CollectionUtils.filter(creatives, creative ->
                creative.getAuditStatus().equals(CommonStatus.VALID.getStatus())
                        && creative.getWidth().equals(width)
                        && creative.getHeight().equals(height)
                        && type.contains(creative.getType()));
    }

    private List<SearchResponse.Creative> buildCreativeResponse(List<CreativeObject> creatives) {
        if (CollectionUtils.isEmpty(creatives)) {
            return Collections.emptyList();
        }
        // 随机获取
        var randomObject = creatives.get(Math.abs(new Random().nextInt() % creatives.size()));
        return Collections.singletonList(SearchResponse.convert(randomObject));
    }
}
