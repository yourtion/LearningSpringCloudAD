package com.yourtion.springcloud.ad.service;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import lombok.var;

import java.io.IOException;

/**
 * @author yourtion
 */
public class BinlogServiceTest {

    public static void main(String[] args) throws IOException {
        var client = new BinaryLogClient("127.0.0.1", 3306, "root", "123456");

//        client.setBinlogFilename();
//        client.setBinlogPosition();
        client.registerEventListener(event -> {
            var data = event.getData();

            if (data instanceof UpdateRowsEventData) {
                System.out.println("Update--------");
                System.out.println(data.toString());
            } else if (data instanceof WriteRowsEventData) {
                System.out.println("Write--------");
                System.out.println(data.toString());
            } else if (data instanceof DeleteRowsEventData) {
                System.out.println("Delete--------");
                System.out.println(data.toString());
            }
        });

        client.connect();
    }
}
