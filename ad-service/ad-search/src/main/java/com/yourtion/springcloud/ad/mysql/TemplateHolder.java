package com.yourtion.springcloud.ad.mysql;

import com.alibaba.fastjson.JSON;
import com.yourtion.springcloud.ad.mysql.constant.OpType;
import com.yourtion.springcloud.ad.mysql.dto.ParseTemplate;
import com.yourtion.springcloud.ad.mysql.dto.TableTemplate;
import com.yourtion.springcloud.ad.mysql.dto.Template;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author yourtion
 */
@Slf4j
@Component
public class TemplateHolder {

    private final JdbcTemplate jdbcTemplate;
    private ParseTemplate template;
    private String SQL_SCHEMA = "select column_name, ordinal_position from information_schema.columns where table_schema = ? and table_name = ?";

    @Autowired
    public TemplateHolder(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    private void init() {
        loadJson("template.json");
        log.info("Load template done");
    }

    public TableTemplate getTable(String tableName) {
        return template.getTableTemplateMap().get(tableName);
    }

    private void loadJson(String path) {
        var cl = Thread.currentThread().getContextClassLoader();
        var inStream = cl.getResourceAsStream(path);
        try {
            Template template = JSON.parseObject(inStream, Charset.defaultCharset(), Template.class);
            this.template = ParseTemplate.parse(template);
            loadMeta();
        } catch (IOException e) {
            log.error((e.getMessage()));
            throw new RuntimeException("fail to parse json file");
        }
    }

    private void loadMeta() {
        for (var entry : template.getTableTemplateMap().entrySet()) {

            var table = entry.getValue();
            var insertFields = table.getOpTypeFieldSetMap().get(OpType.ADD);
            var updateFields = table.getOpTypeFieldSetMap().get(OpType.UPDATE);
            var deleteFields = table.getOpTypeFieldSetMap().get(OpType.DELETE);

            jdbcTemplate.query(SQL_SCHEMA, new Object[]{
                    template.getDatabase(), table.getTableName()
            }, (rs, i) -> {
                int pos = rs.getInt("ORDINAL_POSITION");
                String colName = rs.getString("COLUMN_NAME");

                if (containsInFields(updateFields, colName) || (containsInFields(insertFields, colName)) || (containsInFields(deleteFields, colName))) {
                    table.getPosMap().put(pos - 1, colName);
                }
                return null;
            });

        }
    }

    private boolean containsInFields(List<String> updateFields, String colName) {
        return null != updateFields && updateFields.contains(colName);
    }


}
