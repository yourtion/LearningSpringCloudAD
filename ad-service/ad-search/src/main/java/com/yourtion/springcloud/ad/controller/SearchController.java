package com.yourtion.springcloud.ad.controller;

import com.alibaba.fastjson.JSON;
import com.yourtion.springcloud.ad.annotation.IgnoreResponseAdvice;
import com.yourtion.springcloud.ad.client.SponsorClient;
import com.yourtion.springcloud.ad.client.vo.AdPlan;
import com.yourtion.springcloud.ad.client.vo.AdPlanGetRequest;
import com.yourtion.springcloud.ad.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author yourtion
 */
@Slf4j
@RestController
public class SearchController {

    private final RestTemplate restTemplate;
    private final SponsorClient sponsorClient;

    @Autowired
    public SearchController(RestTemplate restTemplate, SponsorClient sponsorClient) {
        this.restTemplate = restTemplate;
        this.sponsorClient = sponsorClient;
    }

    @IgnoreResponseAdvice
    @PostMapping("/getAdPlansByFeign")
    CommonResponse<List<AdPlan>> getAdPlansByFeign(@RequestBody AdPlanGetRequest request) {
        log.info("getAdPlansByFeign: -> {}", JSON.toJSONString(request));
        return sponsorClient.getAdPlans(request);
    }

    @SuppressWarnings("all")
    @IgnoreResponseAdvice
    @PostMapping("/getAdPlansByRibbon")
    CommonResponse<List<AdPlan>> getAdPlansByRibbon(@RequestBody AdPlanGetRequest request) {
        log.info("getAdPlanByRebbon: -> {}", JSON.toJSONString(request));
        return restTemplate.postForEntity("http://eureka-client-ad-sponsor/ad-sponsor/get/adPlan", request, CommonResponse.class).getBody();
    }

}
