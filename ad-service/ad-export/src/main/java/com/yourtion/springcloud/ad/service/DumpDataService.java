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
import com.yourtion.springcloud.ad.dump.DConstant;
import com.yourtion.springcloud.ad.dump.table.*;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
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

    private final AdPlanRepository planRepository;
    private final AdUnitRepository unitRepository;
    private final CreativeRepository creativeRepository;
    private final CreativeUnitRepository creativeUnitRepository;
    private final AdUnitDistrictRepository districtRepository;
    private final AdUnitItRepository itRepository;
    private final AdUnitKeywordRepository keywordRepository;

    public DumpDataService(AdPlanRepository planRepository, AdUnitRepository unitRepository, CreativeRepository creativeRepository, CreativeUnitRepository creativeUnitRepository, AdUnitDistrictRepository districtRepository, AdUnitItRepository itRepository, AdUnitKeywordRepository keywordRepository) {
        this.planRepository = planRepository;
        this.unitRepository = unitRepository;
        this.creativeRepository = creativeRepository;
        this.creativeUnitRepository = creativeUnitRepository;
        this.districtRepository = districtRepository;
        this.itRepository = itRepository;
        this.keywordRepository = keywordRepository;
    }

    public void dumpAdTableData() {
        log.info("dumpAdTableData start");

        dumpAdPlanTable(String.format("%s/%s", DConstant.DATA_ROOT_DIR, DConstant.AD_PLAN));
        dumpAdUnitTable(String.format("%s/%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT));
        dumpAdCreativeTable(String.format("%s/%s", DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE));
        dumpAdCreativeUnitTable(String.format("%s/%s", DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE_UNIT));
        dumpAdUnitDistrictTable(String.format("%s/%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_DISTRICT));
        dumpAdUnitItTable(String.format("%s/%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_IT));
        dumpAdUnitKeywordTable(String.format("%s/%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_KETWORD));

        log.info("dumpAdTableData done");
    }

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

    private void dumpAdCreativeUnitTable(String filename) {
        var creativeUnits = creativeUnitRepository.findAll();
        if (CollectionUtils.isEmpty(creativeUnits)) {
            return;
        }

        var creativeUnitTables = new ArrayList<AdCreativeUnitTable>();
        creativeUnits.forEach(c ->
                creativeUnitTables.add(new AdCreativeUnitTable(c.getId(), c.getUnitId()))
        );

        writeToFile(filename, creativeUnitTables);
    }

    private void dumpAdUnitDistrictTable(String filename) {
        var districts = districtRepository.findAll();
        if (CollectionUtils.isEmpty(districts)) {
            return;
        }

        var districtTables = new ArrayList<AdUnitDistrictTable>();
        districts.forEach(d ->
                districtTables.add(new AdUnitDistrictTable(d.getUnitId(), d.getProvince(), d.getCity()))
        );

        writeToFile(filename, districtTables);
    }

    private void dumpAdUnitItTable(String filename) {
        var its = itRepository.findAll();
        if (CollectionUtils.isEmpty(its)) {
            return;
        }

        var itTables = new ArrayList<AdUnitItTable>();
        its.forEach(c ->
                itTables.add(new AdUnitItTable(c.getUnitId(), c.getItTag()))
        );

        writeToFile(filename, itTables);
    }

    private void dumpAdUnitKeywordTable(String filename) {
        var keywords = keywordRepository.findAll();
        if (CollectionUtils.isEmpty(keywords)) {
            return;
        }

        var keywordTables = new ArrayList<AdUnitKeywordTable>();
        keywords.forEach(c ->
                keywordTables.add(new AdUnitKeywordTable(c.getUnitId(), c.getKeyword()))
        );

        writeToFile(filename, keywordTables);
    }

}
