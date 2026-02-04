package uz.salikhdev.google_lms.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserCreateRequest(
        @NotNull(message = "First name is required")
        @NotBlank(message = "First name cannot be blank")
        String firstName,
        @NotNull(message = "Last name is required")
        @NotBlank(message = "Last name cannot be blank")
        String lastName,
        @Email(message = "Email should be valid")
        @NotNull(message = "Email is required")
        @NotBlank(message = "Email cannot be blank")
        String email,
        @NotNull(message = "Password is required")
        @NotBlank(message = "Password cannot be blank")
        String password,
        @NotNull
        LocalDate birthDate
) {
}
