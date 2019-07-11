package com.yourtion.springcloud.ad.sender;

import com.yourtion.springcloud.ad.mysql.dto.MySqlRowData;

/**
 * @author yourtion
 */
public interface ISender {

    /**
     * 投递数据
     *
     * @param rowData 数据
     */
    void sender(MySqlRowData rowData);
}
