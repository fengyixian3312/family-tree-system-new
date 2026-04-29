package com.familytree.config;

import com.familytree.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.familytree.repository.UserRepository;
import jakarta.annotation.PostConstruct;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .map(u -> User.withUsername(u.getUsername())
                        .password(u.getPassword())
                        .roles(u.getRole())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在: " + username));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login.html", "/api/auth/**").permitAll()
                        .requestMatchers("/api/family/**").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login.html")
                        .loginProcessingUrl("/api/auth/login")
                        .defaultSuccessUrl("/index.html", true)
                        .failureUrl("/login.html?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessUrl("/login.html")
                        .permitAll()
                );
        return http.build();
    }

    @PostConstruct
    public void init() {
        userService.initAdminUser();
    }
}