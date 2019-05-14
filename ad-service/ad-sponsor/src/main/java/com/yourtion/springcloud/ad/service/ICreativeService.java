package com.yourtion.springcloud.ad.service;

import com.yourtion.springcloud.ad.vo.CreativeRequest;
import com.yourtion.springcloud.ad.vo.CreativeResponse;

/**
 * @author yourtion
 */
public interface ICreativeService {

    /**
     * 创建广告创意
     *
     * @param request 请求
     * @return Creative
     */
    CreativeResponse createCreative(CreativeRequest request);
}
