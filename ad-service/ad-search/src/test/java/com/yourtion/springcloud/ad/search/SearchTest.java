package com.yourtion.springcloud.ad.search;

import com.alibaba.fastjson.JSON;
import com.yourtion.springcloud.ad.SearchTestApplication;
import com.yourtion.springcloud.ad.search.vo.SearchRequest;
import com.yourtion.springcloud.ad.search.vo.feature.DistrictFeature;
import com.yourtion.springcloud.ad.search.vo.feature.FeatureRelation;
import com.yourtion.springcloud.ad.search.vo.feature.ItFeature;
import com.yourtion.springcloud.ad.search.vo.feature.KeywordFeature;
import com.yourtion.springcloud.ad.search.vo.media.AdSlot;
import com.yourtion.springcloud.ad.search.vo.media.App;
import com.yourtion.springcloud.ad.search.vo.media.Device;
import com.yourtion.springcloud.ad.search.vo.media.Geo;
import lombok.var;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author yourtion
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SearchTestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SearchTest {

    @Autowired
    private ISearch search;

    @Test
    public void testFetchAds1() {

        var request = new SearchRequest();
        request.setMediaId("yourtion-id-1");

        // 测试1
        request.setRequestInfo(new SearchRequest.RequestInfo(
                "req",
                Collections.singletonList(
                        new AdSlot("ad-x", 1, 1080, 720, Arrays.asList(1, 2), 1000)
                ),
                buildExampleApp(),
                buildExampleGeo(),
                buildExampleDevice()
        ));
        request.setFeatureInfo(buildExampleFeature(
                Arrays.asList("宝马", "大众"),
                Collections.singletonList(new DistrictFeature.ProvinceAndCity("安徽省", "合肥市")),
                Arrays.asList("台球", "乒乓球"),
                FeatureRelation.OR
        ));
        System.out.println(JSON.toJSONString(request));

        var response = search.fetchAds(request);
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void testFetchAds2() {

        var request = new SearchRequest();
        request.setMediaId("yourtion-id-2");

        // 测试1
        request.setRequestInfo(new SearchRequest.RequestInfo(
                "req",
                Collections.singletonList(
                        new AdSlot("ad-y", 1, 1080, 720, Arrays.asList(1, 2), 1000)
                ),
                buildExampleApp(),
                buildExampleGeo(),
                buildExampleDevice()
        ));
        request.setFeatureInfo(buildExampleFeature(
                Arrays.asList("宝马", "大众", "标志"),
                Collections.singletonList(new DistrictFeature.ProvinceAndCity("安徽省", "合肥市")),
                Arrays.asList("台球", "游泳"),
                FeatureRelation.AND
        ));
        System.out.println(JSON.toJSONString(request));

        var response = search.fetchAds(request);
        System.out.println(JSON.toJSONString(response));
    }

    private SearchRequest.FeatureInfo buildExampleFeature(
            List<String> keywords,
            List<DistrictFeature.ProvinceAndCity> provinceAndCities,
            List<String> its,
            FeatureRelation relation
    ) {
        return new SearchRequest.FeatureInfo(
                new KeywordFeature(keywords),
                new DistrictFeature(provinceAndCities),
                new ItFeature(its),
                relation
        );
    }

    private App buildExampleApp() {
        return new App("gyx", "yourtion", "com.yourtion.app", "video");
    }

    private Geo buildExampleGeo() {
        return new Geo((float) 100.1, (float) 22.22, "广东", "广州");
    }

    private Device buildExampleDevice() {
        return new Device("iPhone", "00xxx", "127.0.0.1", "xxx", "1280*720", "1280*720", "666");
    }
}
