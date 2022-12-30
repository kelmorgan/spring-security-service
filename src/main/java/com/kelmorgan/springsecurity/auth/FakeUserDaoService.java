package com.kelmorgan.springsecurity.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.kelmorgan.springsecurity.security.UserRole.*;


@Repository("fake")
@RequiredArgsConstructor
public class FakeUserDaoService implements UserDao {
    private final PasswordEncoder encoder;

    @Override
    public Optional<UserDetails> selectUserByUserName(String userName) throws UsernameNotFoundException {
        return getUsers().stream()
                .filter(user -> userName.equals(user.getUsername()))
                .findFirst();
    }

    private List<UserDetails> getUsers() {
        return List.of(
                org.springframework.security.core.userdetails.User.builder()
                        .username("john")
                        .password(encoder.encode("password"))
                        .authorities(STUDENT.getGrantedAuthorities())
                        .build(),
                org.springframework.security.core.userdetails.User.builder()
                        .username("tom")
                        .password(encoder.encode("password"))
                        .authorities(ADMINTRAINEE.getGrantedAuthorities())
                        .build(),
                org.springframework.security.core.userdetails.User.builder()
                        .username("linda")
                        .password(encoder.encode("linda123"))
                        .authorities(ADMIN.getGrantedAuthorities())
                        .build()

        );
    }
}
