package uz.salikhdev.google_lms.domain.dto.request;


import lombok.Builder;
import uz.salikhdev.google_lms.domain.entity.academic.HomeworkSubmit;

import java.time.LocalDateTime;

@Builder
public record GroupHomeworkFilterRequest(
        Long homeworkId,
        Long groupId,
        Long creatorId,
        Boolean isSubmitted,
        LocalDateTime fromDate,
        LocalDateTime toDate

) {
}
