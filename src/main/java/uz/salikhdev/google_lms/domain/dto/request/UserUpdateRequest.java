package uz.salikhdev.google_lms.domain.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserUpdateRequest(
        String firstName,
        String lastName,
        @Email(message = "Email should be valid")
        String email,
        LocalDate birthDate
) {
}
