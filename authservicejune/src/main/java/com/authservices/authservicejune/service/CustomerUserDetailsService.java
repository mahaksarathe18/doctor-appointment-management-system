package com.authservices.authservicejune.service;

import com.authservices.authservicejune.entity.User;
import com.authservices.authservicejune.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
@Service
public class CustomerUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public CustomerUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByUsername(username);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername()
                ,user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole()))
        );


    }
}
