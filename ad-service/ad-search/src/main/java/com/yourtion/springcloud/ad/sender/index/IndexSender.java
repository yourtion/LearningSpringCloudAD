package com.yourtion.springcloud.ad.sender.index;

import com.alibaba.fastjson.JSON;
import com.yourtion.springcloud.ad.dump.table.AdCreativeTable;
import com.yourtion.springcloud.ad.dump.table.AdPlanTable;
import com.yourtion.springcloud.ad.handler.AdLevelDataHandler;
import com.yourtion.springcloud.ad.index.DataLevel;
import com.yourtion.springcloud.ad.mysql.constant.Constant;
import com.yourtion.springcloud.ad.mysql.dto.MySqlRowData;
import com.yourtion.springcloud.ad.sender.ISender;
import com.yourtion.springcloud.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yourtion
 */
@Slf4j
@Component("indexSender")
public class IndexSender implements ISender {

    @Override
    public void sender(MySqlRowData rowData) {
        log.debug("indexSender: {}", rowData);
        var level = rowData.getLevel();

        if (DataLevel.LEVEL2.getLevel().equals(level)) {
            level2RowData(rowData);
        } else if (DataLevel.LEVEL3.getLevel().equals(level)) {

        } else if (DataLevel.LEVEL4.getLevel().equals(level)) {

        } else {
            log.error("MySQLRowData ERROR: {}", JSON.toJSONString(rowData));
        }
    }

    private void level2RowData(MySqlRowData rowData) {
        if (rowData.getTableName().equals(
                Constant.AD_PLAN_TABLE_INFO.TABLE_NAME)) {
            List<AdPlanTable> planTables = new ArrayList<>();

            for (Map<String, String> fieldValueMap : rowData.getFieldValueMap()) {

                var planTable = new AdPlanTable();

                fieldValueMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_ID:
                            planTable.setId(Long.valueOf(v));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_USER_ID:
                            planTable.setUserId(Long.valueOf(v));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_PLAN_STATUS:
                            planTable.setPlanStatus(Integer.valueOf(v));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_START_DATE:
                            planTable.setStartDate(CommonUtils.parseStringDate(v));
                            break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_END_DATE:
                            planTable.setEndDate(CommonUtils.parseStringDate(v));
                            break;
                        default:
                    }
                });

                planTables.add(planTable);
            }
            log.debug("level2RowData: AdPlanTable -> {}", planTables);
            planTables.forEach(p -> AdLevelDataHandler.handleLevel2(p, rowData.getOp()));

        } else if (rowData.getTableName().equals(
                Constant.AD_CREATIVE_TABLE_INFO.TABLE_NAME)) {
            List<AdCreativeTable> creativeTables = new ArrayList<>();

            for (Map<String, String> fieldValeMap : rowData.getFieldValueMap()) {

                var creativeTable = new AdCreativeTable();

                fieldValeMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_ID:
                            creativeTable.setAdId(Long.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_TYPE:
                            creativeTable.setType(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_MATERIAL_TYPE:
                            creativeTable.setMaterialType(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_HEIGHT:
                            creativeTable.setHeight(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_WIDTH:
                            creativeTable.setWidth(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_AUDIT_STATUS:
                            creativeTable.setAuditStatus(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_URL:
                            creativeTable.setAdUrl(v);
                            break;
                        default:
                    }
                });

                creativeTables.add(creativeTable);
            }

            log.debug("level2RowData: AdCreativeTable -> {}", creativeTables);
            creativeTables.forEach(c -> AdLevelDataHandler.handleLevel2(c, rowData.getOp()));
        }
    }
}
