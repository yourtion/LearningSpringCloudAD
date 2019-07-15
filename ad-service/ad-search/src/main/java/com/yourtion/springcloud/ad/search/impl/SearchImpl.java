package com.yourtion.springcloud.ad.search.impl;

import com.yourtion.springcloud.ad.index.DataTable;
import com.yourtion.springcloud.ad.index.adunit.AdUnitIndex;
import com.yourtion.springcloud.ad.search.ISearch;
import com.yourtion.springcloud.ad.search.vo.SearchRequest;
import com.yourtion.springcloud.ad.search.vo.SearchResponse;
import com.yourtion.springcloud.ad.search.vo.media.AdSlot;
import lombok.var;

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
        }


        return response;
    }
}
