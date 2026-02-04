package uz.salikhdev.google_lms.domain.dto.response;


import lombok.Builder;
import uz.salikhdev.google_lms.domain.entity.academic.GroupStudentHomeWork;

@Builder
public record GroupStudentHomeWorkResponse(
        Long id,
        String studentFullName,
        Long homeWorkId,
        String homeWorkUrl,
        String description,
        Long score,
        GroupStudentHomeWork.Status status
        ) {
}
