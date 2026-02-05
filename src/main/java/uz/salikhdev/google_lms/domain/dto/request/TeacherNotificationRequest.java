package uz.salikhdev.google_lms.domain.dto.request;


import lombok.Builder;

@Builder
public record TeacherNotificationRequest(
        Long groupNumber,
        String groupName,
        String firstName,
        String lastName
) {
}
