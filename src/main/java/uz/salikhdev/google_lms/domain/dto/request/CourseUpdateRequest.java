package uz.salikhdev.google_lms.domain.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CourseUpdateRequest(
        @NotNull(message = "Name is required")
        @NotBlank(message = "Name cannot be blank")
        String name,
        @NotNull(message = "Price is required")
        BigDecimal price
) {
}
