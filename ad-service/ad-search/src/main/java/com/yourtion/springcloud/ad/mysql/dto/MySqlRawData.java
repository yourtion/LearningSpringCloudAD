package com.yourtion.springcloud.ad.mysql.dto;

import com.yourtion.springcloud.ad.mysql.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yourtion
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MySqlRawData {

    private String tableName;
    private String level;
    private OpType op;
    private List<Map<String, String>> fieldValueMap = new ArrayList<>();

}
