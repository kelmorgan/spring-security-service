package com.kelmorgan.springsecurity.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface UserDao {

    Optional<UserDetails> selectUserByUserName(String userName) throws UsernameNotFoundException;
}
