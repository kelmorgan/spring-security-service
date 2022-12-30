package com.kelmorgan.springsecurity.security;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.kelmorgan.springsecurity.security.UserPermission.*;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(STUDENT_READ, STUDENT_WRITE, COURSE_READ, COURSE_WRITE)),
    ADMINTRAINEE(Sets.newHashSet(STUDENT_READ, COURSE_READ));

    private final Set<UserPermission> permissions;

    public Set<GrantedAuthority> getGrantedAuthorities() {
        Set<GrantedAuthority> permissionsSet = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());

        permissionsSet.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return permissionsSet;
    }

}
