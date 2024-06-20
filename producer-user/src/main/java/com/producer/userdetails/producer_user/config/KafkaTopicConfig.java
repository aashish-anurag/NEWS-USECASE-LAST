package com.producer.userdetails.producer_user.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

	@Bean
    NewTopic newTopic() {
		return TopicBuilder.name("Topic1").build();
	}


}
