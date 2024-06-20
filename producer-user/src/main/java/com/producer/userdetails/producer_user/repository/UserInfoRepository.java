package com.producer.userdetails.producer_user.repository;

import com.producer.userdetails.producer_user.entity.User_Info;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<User_Info, Long> {
}
