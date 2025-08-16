package com.alura.forumhub.config;

import com.alura.forumhub.domain.user.User;
import com.alura.forumhub.domain.user.UserRole;
import com.alura.forumhub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataLoader {

    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initUsers(UserRepository userRepository) {
        return args -> {
            if (!userRepository.existsByUsername("admin@local")) {
                User admin = User.builder()
                        .username("admin@local")
                        .password(passwordEncoder.encode("123456"))
                        .role(UserRole.ROLE_ADMIN)
                        .build();
                userRepository.save(admin);
            }
        };
    }
}
