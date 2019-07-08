package com.yourtion.springcloud.ad.mysql.dto;

import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author yourtion
 */
@Data
public class BinlogRawData {

    private TableTemplate table;
    private EventType eventType;

    private List<Map<String, String>> before;
    private List<Map<String, String>> after;

}
