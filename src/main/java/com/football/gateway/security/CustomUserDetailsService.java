package com.football.gateway.security;


import com.football.common.model.user.User;
import com.football.common.repository.UserRepository;
import com.football.gateway.component.DataAccess;
import com.football.gateway.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: truongnq
 * Date: 2018-12-16
 * Time: 21:50
 * To change this template use File | Settings | File Templates.
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    DataAccess dataAccess;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email : " + email)
                );
        user.setUpdatedAt(new Date());
        dataAccess.saveUser(user);
        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );
        user.setUpdatedAt(new Date());
        dataAccess.saveUser(user);
        return UserPrincipal.create(user);
    }
}