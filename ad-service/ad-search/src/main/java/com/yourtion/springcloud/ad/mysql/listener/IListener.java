package com.yourtion.springcloud.ad.mysql.listener;

import com.yourtion.springcloud.ad.mysql.dto.BinlogRawData;

/**
 * @author yourtion
 */
public interface IListener {

    void register();

    void onEvent(BinlogRawData eventData);
}
