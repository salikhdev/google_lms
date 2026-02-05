package uz.salikhdev.google_lms.domain.dto.request;


import lombok.Builder;

@Builder
public record GroupStudentUpdateRequest(
        Long groupId,
        Long studentId,
        Long groupStudentsId
) {
}
