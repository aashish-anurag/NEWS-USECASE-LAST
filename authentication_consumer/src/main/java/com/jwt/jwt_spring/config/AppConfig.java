package com.jwt.jwt_spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class AppConfig {
/*

    @Bean
    public UserDetailsService userDetailsService(){
      UserDetails user = User.builder().username("Anurag").password(passwordEncoder().encode("Anurag")).roles("ADMIN").build();
      UserDetails user2 = User.builder().username("Anurag2").password(passwordEncoder().encode("Anurag2")).roles("ADMIN").build();

        return new InMemoryUserDetailsManager(user,user2);
    }
*/

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
