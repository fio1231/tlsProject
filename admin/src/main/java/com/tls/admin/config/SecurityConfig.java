package com.tls.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * ==========================================================
 * PackageName : com.tls.admin.config
 * FileName    : SecurityConfig
 * Author      : 이진우
 * Date        : 25. 4. 4.
 * Description :
 * ==========================================================
 * Date          |    Author    |    Note
 * ----------------------------------------------------------
 * 25. 4. 4.    |    이진우    |    최초생성
 */
@Configuration
public class SecurityConfig {

    // 🔐 HTTP 보안 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/instances", "/actuator/**").permitAll()  // 클라이언트 등록 허용
                        .anyRequest().authenticated()                               // 나머지는 인증 필요
                )
                .httpBasic(withDefaults())  // 기본 인증
                .csrf(csrf -> csrf.disable()); // CSRF 비활성화 (등록 문제 방지)

        return http.build();
    }

    // 👤 사용자 정보 등록 (아이디/비번 설정)
    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.withUsername("admin")
                        .password("{noop}admin") // 인코딩 prefix 필요! (noop = 암호화 안함)
                        .roles("USER")
                        .build()
        );
    }
}
