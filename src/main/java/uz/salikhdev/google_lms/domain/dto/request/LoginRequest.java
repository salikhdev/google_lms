package uz.salikhdev.google_lms.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(
        @Email
        @NotBlank(message = "Email cannot be blank")
        @NotNull(message = "Email cannot be null")
        @Schema(example = "salikhdev@gmail.com")
        String email,
        @NotBlank(message = "Password cannot be blank")
        @NotNull(message = "Password cannot be null")
        @Schema(example = "admin123")
        String password
) {
}
