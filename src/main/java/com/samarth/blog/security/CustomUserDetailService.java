package com.samarth.blog.security;

import com.samarth.blog.entity.User;
import com.samarth.blog.exceptions.ResourceNotFoundException;
import com.samarth.blog.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       User user = userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("user","Email "+username,0));

        return user;
    }
}
