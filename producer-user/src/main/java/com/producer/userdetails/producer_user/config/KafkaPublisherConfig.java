/*
package com.producer.userdetails.producer_user.config;


import com.producer.userdetails.producer_user.dto.UserDto;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaPublisherConfig {


    @Bean
    public ProducerFactory<String, UserDto> producerFactory()
    {
        Map<String,Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(ProducerConfig.RETRIES_CONFIG,10);
        configProps.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG,Integer.toString(Integer.MAX_VALUE));
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, UserDto> kafkaTemplate()
    {

        return new KafkaTemplate<>(producerFactory());
    }

*/
/*
    @Bean
    public NewTopic newTopic(){
        return TopicBuilder.name("newTopic")
                .build();
    }
*//*


}
*/
