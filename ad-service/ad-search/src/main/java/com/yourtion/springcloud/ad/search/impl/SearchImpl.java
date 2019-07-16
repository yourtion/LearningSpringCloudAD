package com.yourtion.springcloud.ad.search.impl;

import com.yourtion.springcloud.ad.index.DataTable;
import com.yourtion.springcloud.ad.index.adunit.AdUnitIndex;
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
import lombok.var;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yourtion
 */
public class SearchImpl implements ISearch {

    @Override
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
        }
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
}
