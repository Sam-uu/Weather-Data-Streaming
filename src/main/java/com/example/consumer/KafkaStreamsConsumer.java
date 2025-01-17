// KafkaStreamsConsumer.java
package com.example.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Service
public class KafkaStreamsConsumer {
    private KafkaConsumer<String, String> consumer;

    @PostConstruct
    public void init() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "weather-group");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest"); // start from the latest message

        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList("my_topic_output"));
    }

    public String pollLatestMessage() {
        String latestMessage = null;
        // Polling in a loop to ensure the latest message is fetched
        for (int i = 0; i < 5; i++) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
            for (ConsumerRecord<String, String> record : records) {
                latestMessage = record.value();
            }
            if (latestMessage != null) {
                break;
            }
        }
        return latestMessage;
    }

    public void shutdown() {
        consumer.close();
    }
}
