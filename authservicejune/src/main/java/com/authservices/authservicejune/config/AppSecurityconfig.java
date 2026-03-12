package com.authservices.authservicejune.config;

import com.authservices.authservicejune.service.CustomerUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AppSecurityconfig {
    private CustomerUserDetailsService customerUserDetailsService;
    private JwtFilter filter;

    public AppSecurityconfig(CustomerUserDetailsService customerUserDetailsService, JwtFilter filter, String[] publicEndpoints) {
        this.customerUserDetailsService = customerUserDetailsService;
        this.filter = filter;

    }

    String [] publicEndpoints ={
            "/api/v1/auth/register","/api/v1/auth/login"
    };
    @Bean
    public SecurityFilterChain securityConfig(HttpSecurity http)throws Exception{
        http
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth-> {
                            auth.requestMatchers(publicEndpoints)
                                    .permitAll()
                                    .requestMatchers("/api/v1/message/getmessage").hasAnyRole("ADMIN","USER")
                                    .anyRequest()
                                    .authenticated();
                        }
                ).authenticationProvider(authProvider())
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public PasswordEncoder getEncode(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager
            (AuthenticationConfiguration configuration)throws Exception{
        return configuration.getAuthenticationManager();
    }
    @Bean
    public AuthenticationProvider authProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customerUserDetailsService);
        authProvider.setPasswordEncoder(getEncode());
        return authProvider;
    }
}
