package com.medicalcenter.medicalcentersystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationSuccessHandler successHandler) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // Rule 1: Allow public access to essential pages
                .requestMatchers("/css/**", "/register", "/register-patient", "/login").permitAll()

                // Rule 2: Define patient-specific access
                .requestMatchers("/patient/**").hasRole("PATIENT")
                
                // Rule 3: Define access for Admin and regular User to main pages
                .requestMatchers("/", "/appointments-list", "/doctors-list", "/patients-list", 
                                 "/medicines-list", "/bills-list", "/dashboard", 
                                 "/disease-predictor", "/predict-disease").hasAnyRole("ADMIN", "USER")

                // Rule 4: Define admin-only access for modification actions
                .requestMatchers("/add-*", "/save-*", "/edit-*", "/update-*", "/delete-*", "/generate-bill/**").hasRole("ADMIN")

                // Rule 5: Any other request not specified above must be authenticated
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(successHandler)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }
}