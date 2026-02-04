package uz.salikhdev.google_lms.domain.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import uz.salikhdev.google_lms.domain.entity.user.User;

import java.time.LocalDateTime;

@Builder
public record GroupHomeWorkAttachRequest(
        @NotNull(message = "HomeWork Id cannot be null")
        Long homeworkId,
        @NotNull(message = "Group Id cannot be null")
        Long groupId,
        @NotNull(message = "Deadline is required")
        LocalDateTime deadline

) {
}
