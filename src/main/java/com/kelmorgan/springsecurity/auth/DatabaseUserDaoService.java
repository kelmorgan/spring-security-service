package com.kelmorgan.springsecurity.auth;

import com.kelmorgan.springsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("database")
@RequiredArgsConstructor
public class DatabaseUserDaoService implements UserDao {

    private final UserRepository userRepository;

    @Override
    public Optional<UserDetails> selectUserByUserName(String userName) throws UsernameNotFoundException {
        Optional<com.kelmorgan.springsecurity.domain.User> user = userRepository.findByUserName(userName);

        if (user.isEmpty())
            throw new UsernameNotFoundException(String.format("User with username %s does not exist", userName));

        return Optional.of(getUser(user.get()));
    }

    private UserDetails getUser(com.kelmorgan.springsecurity.domain.User user) {
        return User.builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .authorities(user.getUserRole().getGrantedAuthorities())
                .build();
    }
}
