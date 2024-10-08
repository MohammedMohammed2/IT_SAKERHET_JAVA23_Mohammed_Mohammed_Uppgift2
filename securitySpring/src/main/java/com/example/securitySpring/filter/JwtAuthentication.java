package com.example.securitySpring.filter;

import com.example.securitySpring.service.JwtService;
import com.example.securitySpring.service.DetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Comments;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthentication extends OncePerRequestFilter {


    private final JwtService jwtService;
    private final DetailsService detailsService;

    public JwtAuthentication(JwtService jwtService, DetailsService detailsService) {
        this.jwtService = jwtService;
        this.detailsService = detailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer")){
           filterChain.doFilter(request,response);
           return;
        }
        String token = authHeader.substring(7);
        String username = jwtService.extractUser(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() != null){

            UserDetails user = detailsService.loadUserByUsername(username);

            if (jwtService.isValid(token,user)){
                UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken(
                        user, null , user.getAuthorities()
                );
                authtoken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authtoken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
