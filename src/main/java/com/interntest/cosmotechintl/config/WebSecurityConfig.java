package com.interntest.cosmotechintl.config;

import com.interntest.cosmotechintl.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .securityMatcher("/**")
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/").permitAll()// Publicly accessible endpoints
                                .requestMatchers("/auth/login").permitAll()
//                                .requestMatchers("/**").permitAll()
//                                .requestMatchers("/swagger-ui.html").permitAll()
//                                .requestMatchers("/swagger-ui/index.html").permitAll()
                                .anyRequest().authenticated()       // Protect other endpoints
                )
                .formLogin(form -> form.disable());
        return http.build();
    }

}
