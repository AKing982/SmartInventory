package org.example.smartinventory.config;

import jakarta.servlet.http.HttpServletResponse;
import org.example.smartinventory.workbench.security.EmailPasswordAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http
//                .cors(Customizer.withDefaults())
//                .csrf(Customizer.withDefaults())
                .csrf().disable()
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/api/auth/login").permitAll()
                                .requestMatchers("/api/accounts/{user}").permitAll()
                                .requestMatchers("/api/profile/data/{user}").permitAll()
                                .requestMatchers("/AeroBankApp/dasboard/**").permitAll()
                                .requestMatchers("/ws/**").permitAll()
                                .anyRequest().permitAll()// Require authentication for /home
                        )
                        .exceptionHandling(exceptions -> exceptions
                                .authenticationEntryPoint((request, response, authException) -> {
                                    if (request.getRequestURI().equals("/home")) {
                                        response.sendError(HttpServletResponse.SC_NOT_FOUND);
                                    } else {
                                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                                    }
                                })
                        );

                return http.build();
            }

            @Bean
            public WebMvcConfigurer corsConfigurer()
            {
                return new WebMvcConfigurer() {
                    @Override
                    public void addCorsMappings(CorsRegistry registry) {

                        registry.addMapping("/csrf/token")
                                .allowedOrigins("http://localhost:3000")
                                .allowedMethods("GET")
                                .allowedHeaders("*")
                                .allowCredentials(true);

                        registry.addMapping("/api/**")
                                .allowedOrigins("http://localhost:3000")
                                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                                .allowedHeaders("*")
                                .allowCredentials(true);

                        registry.addMapping("/**")
                                .allowedOrigins("http://localhost:3000")
                                .allowedMethods("GET", "POST")
                                .allowedHeaders("*")
                                .allowCredentials(true);

                        registry.addMapping("/ws/**")
                                .allowedOrigins("http://localhost:3000")
                                .allowedMethods("GET", "POST")
                                .allowedHeaders("*")
                                .allowCredentials(true);


                    }
                };


            }


        }


