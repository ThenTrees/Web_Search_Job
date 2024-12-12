package com.thentrees.lab_week5_www.backend.configurations;

import com.thentrees.lab_week5_www.backend.enums.RoleType;
import com.thentrees.lab_week5_www.backend.models.Candidate;
import com.thentrees.lab_week5_www.backend.models.Role;
import com.thentrees.lab_week5_www.backend.models.User;
import com.thentrees.lab_week5_www.backend.repositories.CandidateRepository;
import com.thentrees.lab_week5_www.backend.repositories.RoleRepository;
import com.thentrees.lab_week5_www.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {
    private final PasswordEncoder passwordEncoder;
    private final CandidateRepository candidateRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    @Bean
    ApplicationRunner applicationRunner() {
        log.info("Initializing application.....");
        return args -> {
            List<Role> roles = new ArrayList<>();
            // init role
            Role userRole =
                    Role.builder().roleType(RoleType.CANDIDATE).build();
            Role adminRole =
                    Role.builder().roleType(RoleType.ADMIN).build();
            Role companyRole =
                    Role.builder().roleType(RoleType.COMPANY).build();
            roles.add(userRole);
            roles.add(adminRole);
            roles.add(companyRole);
            if (roleRepository.findAll().isEmpty()) {
                roleRepository.saveAll(roles);
            }

            if (userRepository.findUserByPhone("0385788328").isEmpty()) {
                    //save all role -> improve performance
                log.info("Creating default user.....");

                Candidate candidate = Candidate.builder()
                        .phone("0385788328")
                        .password(passwordEncoder.encode("admin"))
                        .email("thientri.trank17@gmail.com")
                        .active(true)
                        .role(roleRepository.findById(2L).get())
                        .fullName("admin")
                        .dob(LocalDate.now())
                        .build();
                candidateRepository.save(candidate);

                log.warn("admin user has been created with default password: admin, please change it");
            }
            log.info("Application initialization completed .....");
        };
    }
}
