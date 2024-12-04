package com.thentrees.lab_week5_www.backend.configurations;

import com.thentrees.lab_week5_www.backend.repositories.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CandidateRepository candidateRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return phoneNumber -> candidateRepository
            .findByPhone(phoneNumber)
            .orElseThrow(
                    () -> new UsernameNotFoundException("Cannot find candidate with phone number = " + phoneNumber)
            );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationProvider is used to authenticate user
     * @see DaoAuthenticationProvider: a impl of AuthenticationProvider, which uses UserDetailsService to authenticate user
     * setUserDetailsService(userDetailsService()): Gắn UserDetailsService, nơi logic nạp thông tin người dùng được định nghĩa.
     * setPasswordEncoder(passwordEncoder()): Gắn cơ chế mã hóa mật khẩu. Điều này đảm bảo mật khẩu được xử lý an toàn
     * (so khớp mật khẩu được mã hóa từ cơ sở dữ liệu với mật khẩu người dùng nhập vào).
     * Mục đích: Cung cấp một thành phần xác thực dựa trên cơ sở dữ liệu để Spring Security sử dụng.
     * @return AuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
