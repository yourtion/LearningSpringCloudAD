package com.yourtion.springcloud.ad.mysql.constant;

import com.github.shyiko.mysql.binlog.event.EventType;

/**
 * @author yourtion
 */

public enum OpType {
    /**
     * 添加
     */
    ADD,
    /**
     * 更新
     */
    UPDATE,
    /**
     * 删除
     */
    DELETE,
    /**
     * 其他
     */
    OTHER;

    public static OpType to(EventType eventType) {
        switch (eventType) {
            case EXT_WRITE_ROWS:
                return ADD;
            case EXT_UPDATE_ROWS:
                return UPDATE;
            case EXT_DELETE_ROWS:
                return DELETE;

            default:
                return OTHER;
        }
    }
}
