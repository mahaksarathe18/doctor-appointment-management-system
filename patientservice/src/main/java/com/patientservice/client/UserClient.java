package com.patientservice.client;


import com.patientservice.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "AUTHSERVICEJUNE")
public interface UserClient {

    @GetMapping("/api/v1/auth/getuser")
    User getUserByUsername(@RequestParam("username") String username, @RequestHeader("Authorization") String token);
}
