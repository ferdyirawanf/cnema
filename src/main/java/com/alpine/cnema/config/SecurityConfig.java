package com.alpine.cnema.config;

import com.alpine.cnema.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
//    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter; //belum buat jwtauthenticationfilter
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("api/v1/auth/**", "api/v1/auth/register", "api/v1/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/cinemas", "/api/v1/fnbs", "/api/v1/genres", "/api/v1/movies", "/api/v1/sections").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/cinemas", "/api/v1/fnbs", "/api/v1/genres", "/api/v1/movies", "/api/v1/sections").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/cinemas", "/api/v1/fnbs", "/api/v1/genres", "/api/v1/movies", "/api/v1/sections").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/transactions").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.GET,"/api/v1/recomendations", "/api/v1/transactions/histories", "/api/v1/merch-transactions/histories").hasRole("CUSTOMER")
                        .anyRequest().permitAll()
                )
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(  SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}