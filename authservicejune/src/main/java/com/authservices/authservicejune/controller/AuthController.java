package com.authservices.authservicejune.controller;

import com.authservices.authservicejune.dto.ApiResponse;
import com.authservices.authservicejune.dto.LoginDto;
import com.authservices.authservicejune.dto.UserDto;
import com.authservices.authservicejune.entity.User;
import com.authservices.authservicejune.repository.UserRepository;
import com.authservices.authservicejune.service.AuthService;
import com.authservices.authservicejune.service.JwtService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private  AuthService authService;
    private JwtService jwtService;
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;

    public AuthController(AuthService authService, JwtService jwtService,
                          UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    //http://localhost:8080/api/v1/auth/register
    public ResponseEntity<ApiResponse<String>> register(
            @RequestBody UserDto userdto){
        ApiResponse<String> response =authService.register(userdto);
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatus()));
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> loginView(
            @RequestBody LoginDto loginDto){
        ApiResponse<String> response = new ApiResponse<>();
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken
                        (loginDto.getUsername(),loginDto.getPassword());
        try {
            Authentication authentication =authenticationManager.authenticate(token);
            if(authentication.isAuthenticated()) {
                String jwtToken = jwtService.generateToken(loginDto.getUsername(),
                        authentication.getAuthorities().iterator().next().getAuthority());

                response.setMessage("Login successful");
                response.setStatus(200);
                response.setData(jwtToken);
                return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        response.setMessage("login failed");
        response.setStatus(401);
        response.setData("Un-Authorised user ");
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatus()));
    }
    @GetMapping("/getuser")
    public User getUserByName(@RequestParam String username){
        return userRepository.findByUsername(username);
    }
    }



