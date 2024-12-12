package com.thentrees.lab_week5_www.backend.services.impl;

import com.thentrees.lab_week5_www.backend.exception.ResourceNotFoundException;
import com.thentrees.lab_week5_www.backend.models.User;
import com.thentrees.lab_week5_www.backend.repositories.UserRepository;
import com.thentrees.lab_week5_www.backend.services.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthenticationService implements IAuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    @Override
    public User login(String phone, String password) {
        Optional<User> existUser = userRepository.findUserByPhone(phone);
        if (existUser.isEmpty()) {
            throw new ResourceNotFoundException("Cannot find user with phone number = " + phone);
        }

        if (!existUser.get().isActive()) {
            throw new ResourceNotFoundException("User is not active");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                phone, password,
                existUser.get().getAuthorities());
        // authenticate with Java Spring security

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        authenticationManager.authenticate(authenticationToken);

        return existUser.isPresent() ? existUser.get() : null;
    }
}
