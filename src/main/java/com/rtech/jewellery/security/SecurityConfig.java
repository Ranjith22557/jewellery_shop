package com.rtech.jewellery.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login","/signup","/api/authenticate","/css/**","/js/**","/images/**","/fonts/**","/vendor/**","/favicon.ico","/sales/print/**").permitAll()
                        .requestMatchers("/home","/payment","/purchase","/purchaseHistory","/sales","/salesHistory","/stock","/monthlySales").authenticated()
                        .anyRequest().authenticated()
                )
                .logout(logout ->logout.logoutSuccessUrl("/login").permitAll())
                .addFilterBefore(new FirebaseAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
