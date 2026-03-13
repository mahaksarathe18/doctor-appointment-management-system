package com.authservices.authservicejune.config;

import com.authservices.authservicejune.service.CustomerUserDetailsService;
import com.authservices.authservicejune.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtFilter extends OncePerRequestFilter {
    private JwtService jwtService;
    private CustomerUserDetailsService customerUserDetailsService;

    public JwtFilter(JwtService jwtService, CustomerUserDetailsService customerUserDetailsService) {
        System.out.println("Inside the constructor");

        this.jwtService = jwtService;
        this.customerUserDetailsService = customerUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
       String token= request.getHeader("Authorization");
       if(token != null &&token.startsWith("Bearer ")){
           String rawToken=token.substring(7);
           String username = jwtService.validateTokenAndRetrieveSubject(rawToken);

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
           var userDetails=customerUserDetailsService.loadUserByUsername(username);
           var authToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
       SecurityContextHolder.getContext().setAuthentication(authToken);
        }

       }
       filterChain.doFilter(request,response);
    }
}
