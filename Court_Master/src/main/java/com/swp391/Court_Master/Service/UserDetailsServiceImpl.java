package com.swp391.Court_Master.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.swp391.Court_Master.Entities.AuthenticatedUser;
import com.swp391.Court_Master.Repository.UserRepository;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username){
        AuthenticatedUser user = userRepository.findByEmailOrPhoneNumber(username); 
        if(user == null){
            throw new UsernameNotFoundException("Invalid Email or Phone number");
        }         
        return UserDetailsImbl.build(user, username);
    }

}
