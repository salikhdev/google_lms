package uz.salikhdev.google_lms.domain.dto.request;

import lombok.Builder;
import uz.salikhdev.google_lms.domain.dto.response.HomeworkResponse;

import java.time.LocalDateTime;

@Builder
public record GroupHomeworkResponse(
        Long id,
        HomeworkResponse homework,
        LocalDateTime deadline,
        Boolean isSubmitted,
        LocalDateTime createdAt
) {
}
