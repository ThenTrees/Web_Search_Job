package com.thentrees.lab_week5_www.backend.configurations;

import com.thentrees.lab_week5_www.backend.enums.RoleType;
import com.thentrees.lab_week5_www.backend.models.Candidate;
import com.thentrees.lab_week5_www.backend.models.Role;
import com.thentrees.lab_week5_www.backend.repositories.CandidateRepository;
import com.thentrees.lab_week5_www.backend.repositories.RoleRepository;
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

    @Bean
    ApplicationRunner applicationRunner(CandidateRepository candidateRepository, RoleRepository roleRepository) {
        log.info("Initializing application.....");
        return args -> {
            if (candidateRepository.findByPhone("0385788328").isEmpty()) {
                log.info("Creating default user.....");
                List<Role> roles = new ArrayList<>();
                // init role
                Role userRole =
                        roleRepository.save(Role.builder().roleType(RoleType.CANDIDATE).build());
                Role adminRole =
                        roleRepository.save(Role.builder().roleType(RoleType.ADMIN).build());
                Role companyRole =
                        roleRepository.save(Role.builder().roleType(RoleType.COMPANY).build());
                roles.add(userRole);
                roles.add(adminRole);
                roles.add(companyRole);

                //save all role -> improve performance
                roleRepository.saveAll(roles);

                Candidate candidate = Candidate.builder()
                        .fullName("Admin")
                        .phone("0385788328")
                        .password(passwordEncoder.encode("admin"))
                        .email("thientri.trank17@gmail.com")
                        .dob(LocalDate.now())
                        .active(true)
                        .role(adminRole)
                        .build();
                candidateRepository.save(candidate);
                log.warn("admin user has been created with default password: admin, please change it");
            }
            log.info("Application initialization completed .....");
        };
    }
}
