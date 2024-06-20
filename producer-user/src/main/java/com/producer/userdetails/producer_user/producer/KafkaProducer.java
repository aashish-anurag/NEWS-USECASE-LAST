package com.producer.userdetails.producer_user.producer;

import com.producer.userdetails.producer_user.entity.User_Info;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, User_Info> kafkaTemplate;

    public void sendMessage(User_Info user){
        Message<User_Info> topic1 = MessageBuilder.withPayload(user)
                .setHeader(KafkaHeaders.TOPIC, "Topic1")
                .build();
        kafkaTemplate.send(topic1);
    }

}
