package com.yourtion.springcloud.ad.service;

import com.yourtion.springcloud.ad.entity.AdPlan;
import com.yourtion.springcloud.ad.exception.AdException;
import com.yourtion.springcloud.ad.vo.AdPlanGetRequest;
import com.yourtion.springcloud.ad.vo.AdPlanRequest;
import com.yourtion.springcloud.ad.vo.AdPlanResponse;

import java.util.List;

/**
 * @author yourtion
 */
public interface IAdPlanService {

    /**
     * 创建推广计划
     *
     * @param request 请求
     * @return AdPlan
     * @throws AdException 创建推广计划出错
     */
    AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException;

    /**
     * 获取推广计划
     *
     * @param request 请求
     * @return AdPlan List
     * @throws AdException 获取推广计划出错
     */
    List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException;

    /**
     * 更新推广计划
     *
     * @param request 请求
     * @return AdPlan
     * @throws AdException 更新推广计划出错
     */
    AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException;

    /**
     * 删除推广计划
     *
     * @param request 请求
     * @throws AdException 删除推广计划出错
     */
    void deleteAdPlan(AdPlanRequest request) throws AdException;
}
