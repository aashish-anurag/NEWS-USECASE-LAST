package com.jwt.jwt_spring.kafka;

import com.jwt.jwt_spring.models.User;
import com.jwt.jwt_spring.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Component
@Slf4j
public class KafkaConsumer {

	@Autowired
	UserService userService;

	@KafkaListener(topics = "Topic1", groupId = "myGroup01")
	public void consumeJson(User user) {
		log.info(format("Consuming the User details from Topic1 :: %s", user.toString()));
	}

}
