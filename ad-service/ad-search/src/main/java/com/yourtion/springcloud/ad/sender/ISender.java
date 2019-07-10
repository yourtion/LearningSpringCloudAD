package com.yourtion.springcloud.ad.sender;

import com.yourtion.springcloud.ad.mysql.dto.MySqlRawData;

/**
 * @author yourtion
 */
public interface ISender {

    /**
     * 投递数据
     *
     * @param rawData 数据
     */
    void sender(MySqlRawData rawData);
}
