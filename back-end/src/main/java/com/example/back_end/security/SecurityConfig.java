package com.example.back_end.security;

import java.security.interfaces.RSAPublicKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${rsa.public-key}")
    RSAPublicKey publicKey;

    private JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Disable sessions. We want a stateless application:
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // CSRF protection is merely extra overhead with session management disabled:
                .csrf(csrf -> csrf.disable())

                // All inbound requests must be authenticated:
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/token", "/register").permitAll()
                        .anyRequest().authenticated())

                // Cusomize our resource server to receive JWTs,
                // decoded using our custom decoder above:
                .oauth2ResourceServer(
                        (resourceServer) -> resourceServer.jwt((customizer) -> customizer.decoder(jwtDecoder())))
                .build();
    }
}
