package com.yourtion.springcloud.ad.service;

import com.yourtion.springcloud.ad.SponsorTestApplication;
import com.yourtion.springcloud.ad.exception.AdException;
import com.yourtion.springcloud.ad.vo.AdPlanGetRequest;
import lombok.var;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

/**
 * @author yourtion
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SponsorTestApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AdPlanServiceTest {

    @Autowired
    private IAdPlanService planService;

    @Test
    public void testGetAdPlanByIds() throws AdException {
        var res = planService.getAdPlanByIds(
                new AdPlanGetRequest(15L, Collections.singletonList(10L))
        );

        System.out.println(res);
    }
}
