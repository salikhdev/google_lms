package uz.salikhdev.google_lms.domain.dto.request;

import lombok.Builder;

@Builder
public record HomeWorkUpdateRequest(
        String title,
        String description,
        String contentUrl,
        Long maxScore

) {
}
