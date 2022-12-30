package com.kelmorgan.springsecurity.security;

import com.kelmorgan.springsecurity.security.jwt.CustomAuthenticationFilter;
import com.kelmorgan.springsecurity.security.jwt.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static com.kelmorgan.springsecurity.security.UserPermission.COURSE_WRITE;
import static com.kelmorgan.springsecurity.security.UserRole.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //.csrf().csrfTokenRepository(withHttpOnlyFalse())
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .addFilterBefore(new CustomAuthorizationFilter(), CustomAuthenticationFilter.class)
                .addFilter(new CustomAuthenticationFilter(
                        authenticationManager(http.getSharedObject(AuthenticationConfiguration.class))
                ))
                .authorizeRequests()
                .antMatchers("/", "index").permitAll()
                .antMatchers("/api/v1/**").hasRole(STUDENT.name())
                .antMatchers(DELETE, "/management/api/**").hasAuthority(COURSE_WRITE.name())
                .antMatchers(POST, "/management/api/**").hasAuthority(COURSE_WRITE.name())
                .antMatchers(PUT, "/management/api/**").hasAuthority(COURSE_WRITE.name())
                .antMatchers(GET, "/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
                .anyRequest()
                .authenticated();
//                .addFilter(customAuthenticationFilter);
//                .formLogin()
//                .loginPage("/login").permitAll()
//                .defaultSuccessUrl("/courses", true)
//                .and()
//                .rememberMe()
//                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
//                .key("somethingverysecured")
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .clearAuthentication(true)
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID", "remember-me")
//                .logoutSuccessUrl("/login");


        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
