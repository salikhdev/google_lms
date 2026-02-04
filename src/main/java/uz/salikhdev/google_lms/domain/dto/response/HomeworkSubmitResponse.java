package uz.salikhdev.google_lms.domain.dto.response;


import lombok.Builder;
import uz.salikhdev.google_lms.domain.entity.academic.HomeworkSubmit;

@Builder
public record HomeworkSubmitResponse(
        Long id,
        String studentFullName,
        Long homeWorkId,
        String homeWorkUrl,
        String description,
        Long score,
        HomeworkSubmit.Status status
        ) {
}
