package com.yourtion.springcloud.ad.sender.kafka;

import com.alibaba.fastjson.JSON;
import com.yourtion.springcloud.ad.mysql.dto.MySqlRowData;
import com.yourtion.springcloud.ad.sender.ISender;
import lombok.var;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author yourtion
 */
@Component("kafkaSender")
public class KafkaSender implements ISender {

    private final KafkaTemplate<String, String> kafkaTemplate;
    @Value("${adconf.kafka.topic}")
    private String topic;

    public KafkaSender(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sender(MySqlRowData rowData) {
        kafkaTemplate.send(topic, JSON.toJSONString(rowData));
    }

    @KafkaListener(topics = {"ad-search-mysql-data"}, groupId = "ad-search")
    public void processMySqlRowData(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            var message = kafkaMessage.get();
            var rowData = JSON.parseObject(message.toString(), MySqlRowData.class);
            System.out.println("kafka processMySqlRowData: " + JSON.toJSONString(rowData));
        }
    }
}
