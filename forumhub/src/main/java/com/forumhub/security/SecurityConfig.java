package com.forumhub.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final TokenService tokenService;
    private final UsuarioDetailsService usuarioDetailsService;

    public SecurityConfig(TokenService tokenService, UsuarioDetailsService usuarioDetailsService) {
        this.tokenService = tokenService;
        this.usuarioDetailsService = usuarioDetailsService;
    }

    @Bean
    public SecurityFilter securityFilter() {
        return new SecurityFilter(tokenService, usuarioDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                        .requestMatchers("/usuarios/**").authenticated()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
