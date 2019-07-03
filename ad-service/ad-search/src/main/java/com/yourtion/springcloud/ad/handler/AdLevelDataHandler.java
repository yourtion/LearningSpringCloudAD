package com.yourtion.springcloud.ad.handler;

import com.alibaba.fastjson.JSON;
import com.yourtion.springcloud.ad.dump.table.*;
import com.yourtion.springcloud.ad.index.DataTable;
import com.yourtion.springcloud.ad.index.IndexAware;
import com.yourtion.springcloud.ad.index.adplan.AdPlanIndex;
import com.yourtion.springcloud.ad.index.adplan.AdPlanObject;
import com.yourtion.springcloud.ad.index.adunit.AdUnitIndex;
import com.yourtion.springcloud.ad.index.adunit.AdUnitObject;
import com.yourtion.springcloud.ad.index.creative.CreativeIndex;
import com.yourtion.springcloud.ad.index.creative.CreativeObject;
import com.yourtion.springcloud.ad.index.creativeunit.CreativeUnitIndex;
import com.yourtion.springcloud.ad.index.creativeunit.CreativeUnitObject;
import com.yourtion.springcloud.ad.index.district.UnitDistrictIndex;
import com.yourtion.springcloud.ad.index.interest.UnitItIndex;
import com.yourtion.springcloud.ad.index.keyword.UnitKeywordIndex;
import com.yourtion.springcloud.ad.mysql.constant.OpType;
import com.yourtion.springcloud.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.Collections;
import java.util.HashSet;

/**
 * 层级数据加载器
 * <p>
 * 1. 索引存在层级划分，存在依赖关系
 * 2. 加载全量索引是增量索引"添加"的一种特殊实现
 *
 * @author yourtion
 */
@Slf4j
public class AdLevelDataHandler {

    public static void handleLevel2(AdPlanTable planTable, OpType op) {
        val planObject = new AdPlanObject(
                planTable.getId(),
                planTable.getUserId(),
                planTable.getPlanStatus(),
                planTable.getStartDate(),
                planTable.getEndDate()
        );
        handleBinlogEvent(DataTable.of(AdPlanIndex.class), planObject.getPlanId(), planObject, op);
    }

    public static void handleLevel2(AdCreativeTable creativeTable, OpType op) {
        val creativeObject = new CreativeObject(
                creativeTable.getAdId(),
                creativeTable.getName(),
                creativeTable.getType(),
                creativeTable.getMaterialType(),
                creativeTable.getHeight(),
                creativeTable.getWidth(),
                creativeTable.getAuditStatus(),
                creativeTable.getAdUrl()
        );
        handleBinlogEvent(DataTable.of(CreativeIndex.class), creativeObject.getAdId(), creativeObject, op);
    }

    public static void handleLevel3(AdUnitTable unitTable, OpType op) {
        // 校验AdPlan
        val adPlanObject = DataTable.of(AdPlanIndex.class).get(unitTable.getPlanId());
        if (null == adPlanObject) {
            log.error("handleLevel3 found AdPlanObject error: {}", unitTable.getPlanId());
            return;
        }

        val unitObject = new AdUnitObject(
                unitTable.getUnitId(),
                unitTable.getUnitStatus(),
                unitTable.getPositionType(),
                unitTable.getPlanId(),
                adPlanObject
        );
        handleBinlogEvent(DataTable.of(AdUnitIndex.class), unitTable.getUnitId(), unitObject, op);
    }

    public static void handleLevel3(AdCreativeUnitTable creativeUnitTable, OpType op) {
        // 不支持更新
        if (op == OpType.UPDATE) {
            log.error("CreativeUnitIndex not support update");
            return;
        }

        // 校验 AdUnit / Creative
        val unitObject = DataTable.of(AdUnitIndex.class).get(creativeUnitTable.getUnitId());
        val creativeObject = DataTable.of(CreativeIndex.class).get(creativeUnitTable.getAdId());
        if (null == unitObject || null == creativeObject) {
            log.error("handleLevel3 found AdCreativeUnitTable index error: {}", JSON.toJSONString(creativeUnitTable));
            return;
        }

        val creativeUnitObject = new CreativeUnitObject(
                creativeUnitTable.getAdId(),
                creativeUnitTable.getUnitId()
        );
        val key = CommonUtils.stringConcat(creativeUnitObject.getAdId().toString(), creativeUnitObject.getUnitId().toString());
        handleBinlogEvent(DataTable.of(CreativeUnitIndex.class), key, creativeUnitObject, op);
    }

    public static void handleLevel4(AdUnitDistrictTable districtTable, OpType op) {
        // 不支持更新
        if (op == OpType.UPDATE) {
            log.error("UnitDistrictIndex not support update");
            return;
        }

        // 校验 AdUnit
        val unitObject = DataTable.of(AdUnitIndex.class).get(districtTable.getUnitId());
        if (null == unitObject) {
            log.error("handleLevel4 found UnitDistrictIndex index error: {}", districtTable.getUnitId());
            return;
        }

        val key = CommonUtils.stringConcat(districtTable.getProvince(), districtTable.getCity());
        val value = new HashSet<Long>(Collections.singleton(districtTable.getUnitId()));
        handleBinlogEvent(DataTable.of(UnitDistrictIndex.class), key, value, op);
    }

    public static void handleLevel4(AdUnitItTable itTable, OpType op) {
        // 不支持更新
        if (op == OpType.UPDATE) {
            log.error("UnitItIndex not support update");
            return;
        }

        // 校验 AdUnit
        val unitObject = DataTable.of(AdUnitIndex.class).get(itTable.getUnitId());
        if (null == unitObject) {
            log.error("handleLevel4 found UnitItIndex index error: {}", itTable.getUnitId());
            return;
        }

        val value = new HashSet<Long>(Collections.singleton(itTable.getUnitId()));
        handleBinlogEvent(DataTable.of(UnitItIndex.class), itTable.getItTag(), value, op);
    }

    public static void handleLevel4(AdUnitKeywordTable keywordTable, OpType op) {
        // 不支持更新
        if (op == OpType.UPDATE) {
            log.error("UnitKeywordIndex not support update");
            return;
        }

        // 校验 AdUnit
        val unitObject = DataTable.of(AdUnitIndex.class).get(keywordTable.getUnitId());
        if (null == unitObject) {
            log.error("handleLevel4 found UnitKeywordIndex index error: {}", keywordTable.getUnitId());
            return;
        }

        val value = new HashSet<Long>(Collections.singleton(keywordTable.getUnitId()));
        handleBinlogEvent(DataTable.of(UnitKeywordIndex.class), keywordTable.getKeyword(), value, op);
    }

    private static <K, V> void handleBinlogEvent(IndexAware<K, V> index, K key, V value, OpType op) {
        switch (op) {
            case ADD:
                index.add(key, value);
                break;
            case UPDATE:
                index.update(key, value);
                break;
            case DELETE:
                index.delete(key, value);
                break;
            case OTHER:
            default:
                break;
        }

    }

}
