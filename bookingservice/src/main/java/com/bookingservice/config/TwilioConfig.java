package com.bookingservice.config;
import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

//this config file will help us to Login with twilio
@Configuration
public class TwilioConfig {

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @PostConstruct
    public void init(){
        Twilio.init(accountSid,authToken);
    }
}
