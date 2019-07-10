package com.yourtion.springcloud.ad.mysql.listener;

import com.yourtion.springcloud.ad.mysql.constant.Constant;
import com.yourtion.springcloud.ad.mysql.constant.OpType;
import com.yourtion.springcloud.ad.mysql.dto.BinlogRawData;
import com.yourtion.springcloud.ad.mysql.dto.MySqlRawData;
import com.yourtion.springcloud.ad.sender.ISender;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yourtion
 */
@Slf4j
@Component
public class IncrementListener implements IListener {

    private final AggregationListener aggregationListener;
    @Resource(name = "")
    private ISender sender;

    public IncrementListener(AggregationListener aggregationListener) {
        this.aggregationListener = aggregationListener;
    }

    @Override
    @PostConstruct
    public void register() {

        log.info("IncrementListener register db and table info");
        Constant.table2Db.forEach((k, v) -> aggregationListener.register(v, k, this));
    }

    @Override
    public void onEvent(BinlogRawData eventData) {

        var table = eventData.getTable();
        var eventType = eventData.getEventType();

        // 包装成最后需要投递的数据
        var rawData = new MySqlRawData();
        rawData.setTableName(table.getTableName());
        rawData.setLevel(eventData.getTable().getLevel());
        var op = OpType.to(eventType);
        rawData.setOp(op);

        // 取出模版中该操作对应的字段列表
        var fieldList = table.getOpTypeFieldSetMap().get(op);
        if (null == fieldList) {
            log.warn("{} not support for {}", op, table.getTableName());
            return;
        }

        for (Map<String, String> afterMap : eventData.getAfter()) {

            Map<String, String> fieldValue = new HashMap<>(afterMap.size());

            for (Map.Entry<String, String> entry : afterMap.entrySet()) {
                fieldValue.put(entry.getKey(), entry.getValue());
            }

            rawData.getFieldValueMap().add(fieldValue);
        }

        sender.sender(rawData);
    }
}
