package com.thentrees.lab_week5_www.backend.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Value("${api.prefix}")
    private String apiPrefix;


    @Bean
    public SecurityFilterChain servletFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/","register-company", "register-candidate", "/error").permitAll() // Cho phép truy cập không cần đăng nhập
                        .anyRequest().permitAll() // Còn lại yêu cầu đăng nhập
                )
                .formLogin(
                        formLogin -> formLogin
                                .loginPage("/login")
//                                .loginProcessingUrl("${apiPrefix}/auth/login")
                                .loginProcessingUrl("/api/v1/auth/login")
                                .successHandler(authenticationSuccessHandler())
                                .failureUrl("/error") // Chuyển hướng khi đăng nhập thất bại
                                .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // Đường dẫn logout
                        .logoutSuccessUrl("/") // Chuyển hướng sau khi logout
                        .invalidateHttpSession(true) // Xóa session
                        .deleteCookies("JSESSIONID") // Xóa cookie
                        .clearAuthentication(true) // Xóa thông tin xác thực
                        .permitAll()
                );
        return http.build();
    }

    // Custom Authentication Success Handler để kiểm tra vai trò người dùng
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }
}
