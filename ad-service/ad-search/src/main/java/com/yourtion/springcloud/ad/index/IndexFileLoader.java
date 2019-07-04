package com.yourtion.springcloud.ad.index;

import com.alibaba.fastjson.JSON;
import com.yourtion.springcloud.ad.dump.DConstant;
import com.yourtion.springcloud.ad.dump.table.*;
import com.yourtion.springcloud.ad.handler.AdLevelDataHandler;
import com.yourtion.springcloud.ad.mysql.constant.OpType;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yourtion
 */
@Slf4j
@Component
@DependsOn("dataTable")
public class IndexFileLoader {

    @PostConstruct
    public void init() {
        // 第二层级
        log.info("Load level2");

        val adPlanStrings = loadDumpData(String.format("%s/%s", DConstant.DATA_ROOT_DIR, DConstant.AD_PLAN));
        adPlanStrings.forEach(p -> AdLevelDataHandler.handleLevel2(JSON.parseObject(p, AdPlanTable.class), OpType.ADD));

        val adCreativeStrings = loadDumpData(String.format("%s/%s", DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE));
        adCreativeStrings.forEach(c -> AdLevelDataHandler.handleLevel2(JSON.parseObject(c, AdCreativeTable.class), OpType.ADD));

        // 第三层级
        log.info("Load level3");


        val adUnitStrings = loadDumpData(String.format("%s/%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT));
        adUnitStrings.forEach(u -> AdLevelDataHandler.handleLevel3(JSON.parseObject(u, AdUnitTable.class), OpType.ADD));

        val adCreativeUnitStrings = loadDumpData(String.format("%s/%s", DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE_UNIT));
        adCreativeUnitStrings.forEach(c -> AdLevelDataHandler.handleLevel3(JSON.parseObject(c, AdCreativeUnitTable.class), OpType.ADD));

        // 第四层级
        log.info("Load level");

        val adUnitDistrictStrings = loadDumpData(String.format("%s/%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_DISTRICT));
        adUnitDistrictStrings.forEach(d -> AdLevelDataHandler.handleLevel4(JSON.parseObject(d, AdUnitDistrictTable.class), OpType.ADD));

        val adUnitItStrings = loadDumpData(String.format("%s/%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_IT));
        adUnitItStrings.forEach(i -> AdLevelDataHandler.handleLevel4(JSON.parseObject(i, AdUnitItTable.class), OpType.ADD));

        val adUnitKeywordStrings = loadDumpData(String.format("%s/%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_KETWORD));
        adUnitKeywordStrings.forEach(k -> AdLevelDataHandler.handleLevel4(JSON.parseObject(k, AdUnitKeywordTable.class), OpType.ADD));

    }

    private List<String> loadDumpData(String fileName) {
        try (val br = Files.newBufferedReader(Paths.get(fileName))) {
            return br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
