package com.kelmorgan.springsecurity;


import com.kelmorgan.springsecurity.domain.User;
import com.kelmorgan.springsecurity.repository.UserRepository;
import com.kelmorgan.springsecurity.security.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.kelmorgan.springsecurity.security.UserRole.*;

@SpringBootApplication
@RequiredArgsConstructor
@Configuration
public class SpringSecurityApplication {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }


    @Bean
    CommandLineRunner runner() {
        return arg -> {
            if (repository.count() == 0) {

                repository.save(User.builder()
                        .userName("john")
                        .password(passwordEncoder.encode("password"))
                        .userRole(STUDENT).build());
                repository.save(User.builder()
                        .userName("tom")
                        .password(passwordEncoder.encode("password"))
                        .userRole(ADMINTRAINEE).build());
                repository.save(User.builder()
                        .userName("linda")
                        .password(passwordEncoder.encode("password$123"))
                        .userRole(ADMIN).build());

            }
        };
    }

}
