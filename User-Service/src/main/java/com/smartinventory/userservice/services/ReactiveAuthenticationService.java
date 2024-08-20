package com.smartinventory.userservice.services;

import com.smartinventory.userservice.AuthenticationResponse;
import com.smartinventory.userservice.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ReactiveAuthenticationService implements ReactiveAuthenticationManager
{
    private final ReactiveUserDetailsService userDetailsService;
    private JWTUtil jwtUtil;
    private final Logger LOGGER = LoggerFactory.getLogger(ReactiveAuthenticationService.class);
    private final PasswordEncoder passwordEncoder;

    public ReactiveAuthenticationService(final ReactiveUserDetailsService userDetailsService,
                                         JWTUtil jwtUtil){
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
                .flatMap(auth -> {
                    String username = auth.getName();
                    String password = auth.getCredentials() != null ? auth.getCredentials().toString() : null;

                    if (password == null) {
                        LOGGER.error("Password is null");
                        return Mono.error(new BadCredentialsException("Invalid password"));
                    }

                    return userDetailsService.findByUsername(username)
                            .switchIfEmpty(Mono.error(new BadCredentialsException("User not found")))
                            .flatMap(userDetails -> {
                                if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                                    return Mono.error(new BadCredentialsException("Invalid password"));
                                }
                                return Mono.just(new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities()));
                            });
                });
    }

    public Mono<AuthenticationResponse> createAuthenticationResponse(String username, String password) {
        return authenticate(new UsernamePasswordAuthenticationToken(username, password))
                .cast(UsernamePasswordAuthenticationToken.class)
                .map(authenticatedUser -> {
                    UserDetails userDetails = (UserDetails) authenticatedUser.getPrincipal();
                    String token = jwtUtil.generateToken(userDetails);
                    return new AuthenticationResponse(token, "Bearer", userDetails.getUsername(), userDetails.getAuthorities());
                });
    }
}
