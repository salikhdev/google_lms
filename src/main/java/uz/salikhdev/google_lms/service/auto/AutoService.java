package uz.salikhdev.google_lms.service.auto;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.salikhdev.google_lms.domain.entity.user.User;
import uz.salikhdev.google_lms.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AutoService implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public void run(String @NonNull ... args) {
        User user = User.builder()
                .email("salikhdev@gmail.com")
                .firstName("Muhammadsolih")
                .lastName("Abdugafforov")
                .birthDate(LocalDate.of(2004, 6, 12))
                .password(encoder.encode("admin123"))
                .role(User.Role.SUPER_USER)
                .status(User.Status.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();

        if (!userRepository.existsByEmail(user.getEmail())) {
            userRepository.save(user);
        }
    }


}
