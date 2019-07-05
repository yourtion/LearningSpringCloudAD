package com.yourtion.springcloud.ad.mysql.dto;

import com.yourtion.springcloud.ad.mysql.constant.OpType;
import lombok.Data;
import lombok.var;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author yourtion
 */
@Data
public class ParseTemplate {

    private String database;

    private Map<String, TableTemplate> tableTemplateMap = new HashMap<>();

    public static ParseTemplate parse(Template template) {

        ParseTemplate parseTemplate = new ParseTemplate();
        parseTemplate.setDatabase(template.getDatabase());

        for (var table : template.getTableList()) {

            var name = table.getTableName();
            var level = table.getLevel();

            var tableTemplate = new TableTemplate();
            tableTemplate.setTableName(name);
            tableTemplate.setLevel(level.toString());
            parseTemplate.tableTemplateMap.put(name, tableTemplate);

            // 遍历操作类型对应列
            var opTypeFieldSetMap = tableTemplate.getOpTypeFieldSetMap();

            for (var column : table.getInsert()) {
                getAndCreateIfNeed(OpType.ADD, opTypeFieldSetMap, ArrayList::new)
                        .add(column.getColumn());
            }
            for (var column : table.getUpdate()) {
                getAndCreateIfNeed(OpType.UPDATE, opTypeFieldSetMap, ArrayList::new)
                        .add(column.getColumn());
            }
            for (var column : table.getDelete()) {
                getAndCreateIfNeed(OpType.DELETE, opTypeFieldSetMap, ArrayList::new)
                        .add(column.getColumn());
            }
        }

        return parseTemplate;
    }


    private static <T, R> R getAndCreateIfNeed(T key, Map<T, R> map, Supplier<R> factor) {
        return map.computeIfAbsent(key, k -> factor.get());
    }
}
