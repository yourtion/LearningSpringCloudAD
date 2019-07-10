package com.yourtion.springcloud.ad.mysql;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.yourtion.springcloud.ad.mysql.listener.AggregationListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author yourtion
 */
@Slf4j
@Component
public class BinlogClient {

    private final BinlogConfig config;
    private final AggregationListener listener;
    private BinaryLogClient client;

    public BinlogClient(BinlogConfig config, AggregationListener listener) {
        this.config = config;
        this.listener = listener;
    }


    public void connect() {

        new Thread(() -> {
            client = new BinaryLogClient(
                    config.getHost(),
                    config.getPort(),
                    config.getUsername(),
                    config.getPassword()
            );
            if (!StringUtils.isEmpty(config.getBinlogName()) && !config.getPosition().equals(-1L)) {
                client.setBinlogFilename(config.getBinlogName());
                client.setBinlogPosition(config.getPosition());
            }

            client.registerEventListener(listener);
            try {
                log.info("Connecting to mysql start");
                client.connect();
                log.info("Connecting to mysql done");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();
    }

    public void close() {
        try {
            client.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
