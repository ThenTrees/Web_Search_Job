package com.thentrees.lab_week5_www.backend.configurations;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;

@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Lấy vai trò của người dùng từ đối tượng Authentication
        log.info("User: {}", authentication.getName());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        log.info("Authorities: {}", authorities); // --> [ROLE_ADMIN]
        for (GrantedAuthority authority : authorities) {
            // Kiểm tra vai trò của người dùng và chuyển hướng đến trang tương ứng
            log.info("Role: {}", authority.getAuthority());
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                // Nếu là ADMIN, chuyển hướng đến trang admin.html
                response.sendRedirect("/admin");
                return;
            } else if (authority.getAuthority().equals("ROLE_CANDIDATE")) {
                // Nếu là CANDIDATE, chuyển hướng đến trang candidate.html
                response.sendRedirect("/");
                return;
            } else if (authority.getAuthority().equals("ROLE_COMPANY")) {
                // Nếu là CANDIDATE, chuyển hướng đến trang candidate.html
                response.sendRedirect("/companies/show");
                return;
            }
        }

        log.info("No role found");
        // Nếu không khớp vai trò nào, chuyển hướng đến trang mặc định
        response.sendRedirect("/");
    }
}
