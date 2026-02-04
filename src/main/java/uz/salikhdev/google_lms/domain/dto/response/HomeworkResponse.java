package uz.salikhdev.google_lms.domain.dto.response;

import lombok.Builder;

@Builder
public record HomeworkResponse(
        Long id,
        String title,
        String description,
        String contentUrl,
        Long maxScore
) {
}
