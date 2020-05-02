package com.github.niupengyu.commons.kafka;

import com.github.niupengyu.core.util.IdGeneratorUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Arrays;
import java.util.Properties;


public class KafkaProTest {

    Producer<String, String> producer;

    private String host = "localhost:9092";
    private String group = "test-notify";
    private String topic = "switch.user.state";


    public void producer(String str, String str1) {
        Properties props = new Properties();
        props.put("bootstrap.servers", host);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer<String, String>(props);
        System.out.println("send " + str + " " + str1);
        producer.send(new ProducerRecord<String, String>(topic, str, str1));
        producer.close();
    }

    public static void main(String[] args) {
        KafkaProTest kafkaTest = new KafkaProTest();
        kafkaTest.producer(IdGeneratorUtil.uuid32(), System.currentTimeMillis() + "");

    }


}