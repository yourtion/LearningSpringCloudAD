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
import com.yourtion.springcloud.ad.dump.table.AdCreativeTable;
import com.yourtion.springcloud.ad.dump.table.AdPlanTable;
import com.yourtion.springcloud.ad.dump.table.AdUnitTable;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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

    private void writeToFile(String filename, List list) {
        var path = Paths.get(filename);
        try (var writer = Files.newBufferedWriter(path)) {
            for (var item : list) {
                writer.write(JSON.toJSONString(item));
                writer.newLine();
            }
        } catch (IOException e) {
            log.error("writeToFile error: " + filename);
        }
    }

    private void dumpAdPlanTable(String filename) {
        var adPlans = planRepository.findAllByPlanStatus(CommonStatus.VALID.getStatus());
        if (CollectionUtils.isEmpty(adPlans)) {
            return;
        }

        var planTables = new ArrayList<AdPlanTable>();
        adPlans.forEach(p ->
                planTables.add(new AdPlanTable(p.getId(), p.getUserId(), p.getPlanStatus(), p.getStartDate(), p.getEndDate()))
        );

        writeToFile(filename, planTables);
    }

    private void dumpAdUnitTable(String filename) {
        var adUnits = unitRepository.findAllByUnitStatus(CommonStatus.VALID.getStatus());
        if (CollectionUtils.isEmpty(adUnits)) {
            return;
        }

        var unitTables = new ArrayList<AdUnitTable>();
        adUnits.forEach(u ->
                unitTables.add(new AdUnitTable(u.getId(), u.getUnitStatus(), u.getPositionType(), u.getPlanId()))
        );

        writeToFile(filename, unitTables);
    }

    private void dumpAdCreativeTable(String filename) {
        var creatives = creativeRepository.findAll();
        if (CollectionUtils.isEmpty(creatives)) {
            return;
        }

        var creativeTables = new ArrayList<AdCreativeTable>();
        creatives.forEach(c ->
                creativeTables.add(new AdCreativeTable(c.getId(), c.getName(), c.getType(), c.getMaterialType(), c.getHeight(), c.getWidth(), c.getAuditStatus(), c.getUrl()))
        );

        writeToFile(filename, creativeTables);
    }

}
