package com.pragma.plazoleta.infrastructure.configuration;

import com.pragma.plazoleta.application.dto.request.UserRequestDto;
import com.pragma.plazoleta.application.handler.IJwtHandler;
import com.pragma.plazoleta.application.mapper.IUserRequestMapper;
import com.pragma.plazoleta.domain.model.User;
import com.pragma.plazoleta.infrastructure.input.rest.client.IUserFeignClient;
import com.pragma.plazoleta.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.plazoleta.infrastructure.out.jpa.mapper.IUserEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final IJwtHandler jwtHandler;
    private final UserDetailsService userDetailsService;
    private final IUserFeignClient userRepository;

    private final IUserRequestMapper iUserRequestMapper;
    private final IUserEntityMapper iUserEntityMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        userEmail = jwtHandler.extractUserName(jwt);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserRequestDto user = userRepository.getByEmail(userEmail).getBody().getData();
            User userModel = iUserRequestMapper.toUser(user);
            UserEntity userEntity = iUserEntityMapper.toEntity(userModel);
            UserDetails userDetails = userEntity;
            if (jwtHandler.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}