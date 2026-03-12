package com.authservices.authservicejune.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class Welcome {

    @GetMapping("/welcome")
    public String welcome(){
        return "welcome";
    }
}
