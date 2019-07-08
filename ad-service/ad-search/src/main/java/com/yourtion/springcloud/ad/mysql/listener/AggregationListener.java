package com.yourtion.springcloud.ad.mysql.listener;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.EventType;
import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import com.yourtion.springcloud.ad.mysql.TemplateHolder;
import com.yourtion.springcloud.ad.mysql.dto.BinlogRawData;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class AggregationListener implements BinaryLogClient.EventListener {

    private final TemplateHolder templateHolder;
    private String dbName;
    private String tableName;
    private Map<String, IListener> listenerMap = new HashMap<>();

    public AggregationListener(TemplateHolder templateHolder) {
        this.templateHolder = templateHolder;
    }

    private String genKey(String dbName, String tableName) {
        return dbName + ":" + tableName;
    }

    private void register(String db, String table, IListener listener) {
        log.info("register : {}-{}", db, table);
        this.listenerMap.put(genKey(db, table), listener);
    }

    @Override
    public void onEvent(Event event) {

        var type = event.getHeader().getEventType();
        log.debug("event type: {}", type);

        if (type == EventType.TABLE_MAP) {
            TableMapEventData data = event.getData();
            this.dbName = data.getDatabase();
            this.tableName = data.getTable();
            return;
        }

        // 如果不是更新、插入、删除操作，直接忽略（跟不同办不到MySQL有关，此处使用8.0）
        if (type != EventType.EXT_UPDATE_ROWS && type != EventType.EXT_WRITE_ROWS && type != EventType.EXT_DELETE_ROWS) {
            return;
        }

        // 表名、库名是否完成填充
        if (StringUtils.isEmpty(dbName) || StringUtils.isEmpty(tableName)) {
            log.error("no meta data event");
            return;
        }

        // 找出有兴趣的监听器
        var key = genKey(this.dbName, this.tableName);
        var listener = this.listenerMap.get(key);

        try {
            if (null == listener) {
                log.debug("skip {}", key);
                return;
            }
            log.info("trigger event: {}", type.name());

            var rawData = buildRawData(event.getData());
            if (null == rawData) {
                return;
            }

            rawData.setEventType(type);
            listener.onEvent(rawData);

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            this.dbName = "";
            this.tableName = "";
        }
    }

    private BinlogRawData buildRawData(EventData data) {
        // TODO: 完成方法
        return null;
    }
}
