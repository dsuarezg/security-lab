package com.ironhack.spring_security.filters;

import com.ironhack.spring_security.services.JwtService;
import com.ironhack.spring_security.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;


    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token =  authHeader.substring(7);

        if (!jwtService.verifyToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtService.getUsername(token);
        String roles = jwtService.getRoles(token);

        Collection<GrantedAuthority> authorities = extractAuthorities(roles);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);

    }

    private Collection<GrantedAuthority> extractAuthorities(String roles) {

        if (roles == null || roles.isEmpty()) {
            return Collections.emptyList();
        }

        String rolesString = roles.replace("[", "").replace("]", "");
        String[] rolesArray = rolesString.split(",");

        return Arrays.stream(rolesArray)
                .map(String::trim)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

    }

    }

