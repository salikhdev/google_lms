package uz.salikhdev.google_lms.domain.dto.request;

import lombok.Builder;
import uz.salikhdev.google_lms.domain.entity.academic.HomeworkSubmit;


@Builder
public record HomeworkSubmitFilterRequest(
        String search,
        Long studentId,
        Long homeWorkId,
        Long groupId,
        Long fromScore,
        Long toScore,
        HomeworkSubmit.Status status
) {
}
