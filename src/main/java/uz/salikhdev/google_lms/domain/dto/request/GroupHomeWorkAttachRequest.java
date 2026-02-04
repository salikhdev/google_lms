package uz.salikhdev.google_lms.domain.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record GroupHomeWorkAttachRequest(
        @NotNull(message = "HomeWork Id cannot be null")
        Long homeworkId,
        @NotNull(message = "Deadline is required")
        LocalDateTime deadline
) {
}
