package com.yourtion.springcloud.ad.handler;

import com.yourtion.springcloud.ad.index.IndexAware;
import com.yourtion.springcloud.ad.mysql.constant.OpType;
import lombok.extern.slf4j.Slf4j;

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

    private static <K, V> void handleBinlogEvent(IndexAware<K, V> index, K key, V value, OpType type) {
        switch (type) {
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
