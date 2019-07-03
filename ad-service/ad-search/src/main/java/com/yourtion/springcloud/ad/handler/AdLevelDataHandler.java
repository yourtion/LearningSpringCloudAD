package com.yourtion.springcloud.ad.handler;

import com.yourtion.springcloud.ad.dump.table.AdCreativeTable;
import com.yourtion.springcloud.ad.dump.table.AdPlanTable;
import com.yourtion.springcloud.ad.index.DataTable;
import com.yourtion.springcloud.ad.index.IndexAware;
import com.yourtion.springcloud.ad.index.adplan.AdPlanIndex;
import com.yourtion.springcloud.ad.index.adplan.AdPlanObject;
import com.yourtion.springcloud.ad.index.creative.CreativeIndex;
import com.yourtion.springcloud.ad.index.creative.CreativeObject;
import com.yourtion.springcloud.ad.mysql.constant.OpType;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

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
