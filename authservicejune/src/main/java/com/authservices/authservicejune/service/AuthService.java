package com.authservices.authservicejune.service;

import com.authservices.authservicejune.dto.ApiResponse;
import com.authservices.authservicejune.dto.UserDto;
import com.authservices.authservicejune.entity.User;
import com.authservices.authservicejune.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private UserRepository userRepository;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private PasswordEncoder passwordEncoder;

    public ApiResponse<String> register(UserDto dto){
        if(userRepository.existsByUsername(dto.getUsername())){
            ApiResponse<String> response = new ApiResponse<>();
            response.setMessage("Registration failed");
            response.setStatus(500);
            response.setData("User already exists");
            return response;
        }

        if(userRepository.existsByEmail(dto.getEmail())){
            ApiResponse<String> response= new ApiResponse<>();
            response.setMessage("Registration failed");
            response.setStatus(500);
            response.setData("email already exists");
            return response;
        }
        User user = new User();
        BeanUtils.copyProperties(dto,user);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);

        ApiResponse<String> response = new ApiResponse<>();
        response.setMessage("Registration completed" );
        response.setStatus(201);
        response.setData("user is created");
        return response;

    }
}
