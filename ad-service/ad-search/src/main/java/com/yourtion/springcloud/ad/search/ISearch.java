package com.yourtion.springcloud.ad.search;

import com.yourtion.springcloud.ad.search.vo.SearchRequest;
import com.yourtion.springcloud.ad.search.vo.SearchResponse;

/**
 * @author yourtion
 */
public interface ISearch {

    /**
     * 获取广告信息
     */
    SearchResponse fetchAds(SearchRequest request);
}
