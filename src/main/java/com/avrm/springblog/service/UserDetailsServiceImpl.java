package com.avrm.springblog.service;

import com.avrm.springblog.model.User;
import com.avrm.springblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {                 // custom userDetailService

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                                  .orElseThrow(() -> new UsernameNotFoundException("No username found :" + username));


        // fully qualified name of this class User object, expect us to provide what authorities this user provide
        return new org.springframework.security.core.userdetails
                                                    .User(user.getUsername(), user.getPassword(),
                                                    true, true,
                                                    true, true,
                                                    getAuthorities("ROLE_USER"));
    }

    // we simply pass role.username and we granted authority object for this role and returning back a singleton list
    private Collection<? extends GrantedAuthority> getAuthorities(String role_user) {
        return Collections.singletonList(new SimpleGrantedAuthority(role_user));
    }
}
