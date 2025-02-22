package com.jwt.jwt_spring.service;

import com.jwt.jwt_spring.models.User;
import com.jwt.jwt_spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //load user details from database
       User user = userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not found !!"));
       return user;
    }
}
