package com.producer.userdetails.producer_user.service;


import com.producer.userdetails.producer_user.entity.User_Info;
import com.producer.userdetails.producer_user.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserInfoRepository userRepository;
    @Override
    public User_Info createUser(User_Info user) {

        return userRepository.save(user);
    }
}
