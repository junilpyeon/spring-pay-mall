package com.jpabook.jpashop.security.securityService;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class loginService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        //userId 를 이용하여 DB를 조회한다.
        //성공일경우 user 객체 반환
        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        if ("user".equals(userId)) {
            roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        } else if ("admin".equals(userId)) {
            roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            return null;
        }
        return new User(userId, userId, roles);
    }
}
