package com.bookingservice.config;


import com.bookingservice.client.UserClient;
import com.bookingservice.dto.User;
import com.bookingservice.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {


    private JwtService jwtService;
    private UserClient userClient;

    public JwtFilter(JwtService jwtService, UserClient userClient) {
        this.jwtService = jwtService;
        this.userClient = userClient;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            String username = jwtService.validateTokenAndRetrieveSubject(jwt);

            User user = userClient.getUserByUsername(username,authHeader);

            var authToken = new UsernamePasswordAuthenticationToken(
                    user, null, Collections.singleton(new SimpleGrantedAuthority(user.getRole())));

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }
}
