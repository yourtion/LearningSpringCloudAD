package com.yourtion.springcloud.ad.service;

import com.alibaba.fastjson.JSON;
import com.yourtion.springcloud.ad.constant.CommonStatus;
import com.yourtion.springcloud.ad.dao.AdPlanRepository;
import com.yourtion.springcloud.ad.dao.AdUnitRepository;
import com.yourtion.springcloud.ad.dao.CreativeRepository;
import com.yourtion.springcloud.ad.dao.unit.AdUnitDistrictRepository;
import com.yourtion.springcloud.ad.dao.unit.AdUnitItRepository;
import com.yourtion.springcloud.ad.dao.unit.AdUnitKeywordRepository;
import com.yourtion.springcloud.ad.dao.unit.CreativeUnitRepository;
import com.yourtion.springcloud.ad.dump.table.AdPlanTable;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * @author yourtion
 */
@Slf4j
@Component
public class DumpDataService {

    @Autowired
    private AdPlanRepository planRepository;
    @Autowired
    private AdUnitRepository unitRepository;
    @Autowired
    private CreativeRepository creativeRepository;
    @Autowired
    private CreativeUnitRepository creativeUnitRepository;
    @Autowired
    private AdUnitDistrictRepository districtRepository;
    @Autowired
    private AdUnitItRepository itRepository;
    @Autowired
    private AdUnitKeywordRepository keywordRepository;

    private void dumpAdPlanTable(String filename) {
        var adPlans = planRepository.findAllByPlanStatus(CommonStatus.VALID.getStatus());

        if (CollectionUtils.isEmpty(adPlans)) {
            return;
        }

        var planTables = new ArrayList<AdPlanTable>();
        adPlans.forEach(p ->
                planTables.add(new AdPlanTable(p.getId(), p.getUserId(), p.getPlanStatus(), p.getStartDate(), p.getEndDate()))
        );

        var path = Paths.get(filename);
        try (var writer = Files.newBufferedWriter(path)) {
            for (var planTable : planTables) {
                writer.write(JSON.toJSONString(planTable));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("dumpAdPlanTable error");
        }
    }

}
