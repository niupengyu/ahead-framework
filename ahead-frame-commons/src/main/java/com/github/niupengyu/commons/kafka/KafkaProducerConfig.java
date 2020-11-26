package com.github.niupengyu.commons.kafka;

import com.github.niupengyu.core.annotation.AutoConfig;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import javax.security.auth.kerberos.KerberosKey;
import javax.security.auth.kerberos.KerberosPrincipal;
import java.util.HashMap;
import java.util.Map;

/**
 * kafka消费者配置
 *
 * @author 
 */
@Configuration
@AutoConfig(name = "kafka.producer.enable")
//@EnableKafka
public class KafkaProducerConfig {

    @Value("${kafka.bootstrap-servers}")
    private String brokerAddress;

    @Value("${kafka.producer.client-id}")
    private String clientId;

    @Value("${kafka.producer.security-protocol}")
    private String securityProtocol;

    @Value("${kafka.producer.sasl-kerberos-service-name}")
    private String saslKerberosServiceName;

    @Value("${kafka.producer.kerberos-domain-name}")
    private String kerberosDomainName;

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.brokerAddress);
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.CLIENT_ID_CONFIG,clientId);
        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG,securityProtocol);
        props.put(SaslConfigs.SASL_KERBEROS_SERVICE_NAME,saslKerberosServiceName);
        props.put("kerberos.domain.name",kerberosDomainName);

        return props;
    }
}