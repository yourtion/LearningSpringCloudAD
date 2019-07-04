package com.yourtion.springcloud.ad.index;

import com.alibaba.fastjson.JSON;
import com.yourtion.springcloud.ad.dump.DConstant;
import com.yourtion.springcloud.ad.dump.table.AdCreativeTable;
import com.yourtion.springcloud.ad.dump.table.AdCreativeUnitTable;
import com.yourtion.springcloud.ad.dump.table.AdPlanTable;
import com.yourtion.springcloud.ad.dump.table.AdUnitTable;
import com.yourtion.springcloud.ad.handler.AdLevelDataHandler;
import com.yourtion.springcloud.ad.mysql.constant.OpType;
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
@Component
@DependsOn("dataTable")
public class IndexFileLoader {

    @PostConstruct
    public void init() {
        // 第二层级

        val adPlanStrings = loadDumpData(String.format("%s/%s", DConstant.DATA_ROOT_DIR, DConstant.AD_PLAN));
        adPlanStrings.forEach(p -> AdLevelDataHandler.handleLevel2(JSON.parseObject(p, AdPlanTable.class), OpType.ADD));

        val adCreativeStrings = loadDumpData(String.format("%s/%s", DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE));
        adCreativeStrings.forEach(p -> AdLevelDataHandler.handleLevel2(JSON.parseObject(p, AdCreativeTable.class), OpType.ADD));

        // 第三层级

        val adunitStrings = loadDumpData(String.format("%s/%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT));
        adunitStrings.forEach(p -> AdLevelDataHandler.handleLevel3(JSON.parseObject(p, AdUnitTable.class), OpType.ADD));

        val adCreativeUnitStrings = loadDumpData(String.format("%s/%s", DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE_UNIT));
        adCreativeUnitStrings.forEach(p -> AdLevelDataHandler.handleLevel3(JSON.parseObject(p, AdCreativeUnitTable.class), OpType.ADD));

    }

    private List<String> loadDumpData(String fileName) {
        try (val br = Files.newBufferedReader(Paths.get(fileName))) {
            return br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
