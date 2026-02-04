package uz.salikhdev.google_lms.domain.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record GroupStudentHomeWorkCreateRequest(
        String description,
        @NotNull(message = "Homework url cannot be null")
        String homeWorkUrl,
        @NotNull(message = "Homework id cannot be null")
        Long homeWorkId,
        @NotNull(message = "Group id cannot be null")
        Long groupId






) {
}
