package com.yourtion.springcloud.ad.service;

import com.yourtion.springcloud.ad.exception.AdException;
import com.yourtion.springcloud.ad.vo.*;

/**
 * @author yourtion
 */
public interface IAdUnitService {

    /**
     * 创建推广单元
     *
     * @param request 请求
     * @return AdUnit
     * @throws AdException 创建推广单元出错
     */
    AdUnitResponse createUnit(AdUnitRequest request) throws AdException;

    /**
     * 创建关键字维度推广单元
     *
     * @param request 请求
     * @return AdUnitKeyword
     * @throws AdException 创建关键字维度推广单元出错
     */
    AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request) throws AdException;

    /**
     * 创建兴趣维度推广单元
     *
     * @param request 请求
     * @return AdUnitIt
     * @throws AdException 创建兴趣维度推广单元出错
     */
    AdUnitItResponse createUnitIt(AdUnitItRequest request) throws AdException;

    /**
     * 创建地域维度推广单元
     *
     * @param request 请求
     * @return AdUnitDistrict
     * @throws AdException 创建地域维度推广单元出错
     */
    AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest request) throws AdException;

    /**
     * 创意与推广单元关联
     *
     * @param request 请求
     * @return CreativeUnit
     * @throws AdException 创意与推广单元关联出错
     */
    CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws AdException;
}
