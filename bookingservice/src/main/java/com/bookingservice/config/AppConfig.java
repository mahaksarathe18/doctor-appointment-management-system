package com.bookingservice.config;

import com.bookingservice.config.JwtFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AppConfig {

    public AppConfig(JwtFilter filter) {
        this.filter = filter;
    }

    private JwtFilter filter;

        String[] publicEndpoints = {
                "/api/v1/booking/getdoctor?doctorId=1&patientId=1&date=2026-02-18&time=14:00"
        };

        @Bean
        public PasswordEncoder getEncoder() {
            return new BCryptPasswordEncoder();
        }

    @Bean
    public SecurityFilterChain securityConfig(HttpSecurity http)throws Exception{
        http
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth-> {
                            auth.requestMatchers(publicEndpoints)
                                    .permitAll()
                                    .requestMatchers("/api/v1/booking/getdoctor?doctorId=1&patientId=1&date=2026-02-18&time=14:00").hasAnyRole("ADMIN","USER")
                                    .anyRequest()
                                    .authenticated();
                        }
                )
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
