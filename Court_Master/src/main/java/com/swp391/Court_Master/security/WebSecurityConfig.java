package com.swp391.Court_Master.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain applicationSecurity(HttpSecurity http) throws Exception{
        http
            .cors(cors -> {
                CorsConfigurationSource source = request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(Arrays.asList("*")); // Cho phép tất cả các nguồn gốc
                    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Cho phép các phương thức HTTP
                    config.setAllowedHeaders(Arrays.asList("*")); // Cho phép tất cả các header
                    return config;
                };
                cors.configurationSource(source);
            })
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(sessionManagement -> 
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .formLogin(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorize -> 
                authorize
                    .requestMatchers("/**").permitAll()
                    .anyRequest().authenticated()
            );
        return http.build();
    }
}
