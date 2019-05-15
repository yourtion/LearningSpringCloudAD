package com.yourtion.springcloud.ad.client;

import com.yourtion.springcloud.ad.client.vo.AdPlan;
import com.yourtion.springcloud.ad.client.vo.AdPlanGetRequest;
import com.yourtion.springcloud.ad.vo.CommonResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yourtion
 */
@Component
public class SponsorClientHystrix implements SponsorClient {
    @Override
    public CommonResponse<List<AdPlan>> getAdPlans(AdPlanGetRequest request) {
        return new CommonResponse<>(-1, "eureka-client-ad-sponsor error");
    }
}
