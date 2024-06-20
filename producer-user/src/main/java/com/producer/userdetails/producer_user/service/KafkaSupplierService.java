package com.producer.userdetails.producer_user.service;

import com.producer.userdetails.producer_user.dto.UserDto;
import com.producer.userdetails.producer_user.entity.User_Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KafkaSupplierService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaSupplierService.class);
    @Autowired
//    private KafkaTemplate<String, UserDto> kafkaTemplate;
    private final static String TOPIC_NAME = "NewTopic";
    public void sendMessage(UserDto user)
    {
        logger.info("====Kafka send method called ===={}",user);

  //      kafkaTemplate.send(TOPIC_NAME,user);
    }
}
