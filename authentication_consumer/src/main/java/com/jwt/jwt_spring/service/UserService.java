package com.jwt.jwt_spring.service;

import com.jwt.jwt_spring.models.User;
import com.jwt.jwt_spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //private List<User> store = new ArrayList<>();
    /*
    public UserService(){
        store.add(new User(UUID.randomUUID().toString(),"Durgesh","durgesh@gmail.com"));
        store.add(new User(UUID.randomUUID().toString(),"Durgesh2","durgesh2@gmail.com"));
        store.add(new User(UUID.randomUUID().toString(),"Durgesh3","durgesh3@gmail.com"));
        store.add(new User(UUID.randomUUID().toString(),"Durgesh3","durgesh3@gmail.com"));
    }*/

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User createUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    //
}
