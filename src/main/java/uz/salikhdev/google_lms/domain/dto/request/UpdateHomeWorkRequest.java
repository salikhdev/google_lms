package uz.salikhdev.google_lms.domain.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UpdateHomeWorkRequest(
        String title,
        String description,
        String contentUrl,
        Long maxScore

) {
}
