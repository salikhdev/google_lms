package uz.salikhdev.google_lms.domain.dto.request;


import lombok.Builder;
import uz.salikhdev.google_lms.domain.entity.academic.GroupStudentHomeWork;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record GroupStudentHomeWorkFilterRequest(
        String search,
        Long studentId,
        Long homeWorkId,
        Long groupId,
        Long fromScore,
        Long toScore,
        GroupStudentHomeWork.Status status

) {
}
