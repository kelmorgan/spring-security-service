package com.kelmorgan.springsecurity.domain;

import com.kelmorgan.springsecurity.security.UserRole;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String userName;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;
}
