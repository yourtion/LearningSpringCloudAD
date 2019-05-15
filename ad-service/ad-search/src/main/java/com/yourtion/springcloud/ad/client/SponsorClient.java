package com.yourtion.springcloud.ad.client;

import com.yourtion.springcloud.ad.client.vo.AdPlan;
import com.yourtion.springcloud.ad.client.vo.AdPlanGetRequest;
import com.yourtion.springcloud.ad.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author yourtion
 */
@FeignClient(value = "eureka-client-ad-sponsor", fallback = SponsorClientHystrix.class)
public interface SponsorClient {

    /**
     * 通过Feign获取AdPlan列表
     *
     * @param request 请求
     * @return AdPlan列表
     */
    @RequestMapping(value = "/ad-sponsor/get/adPlan", method = RequestMethod.POST)
    CommonResponse<List<AdPlan>> getAdPlans(@RequestBody AdPlanGetRequest request);
}
