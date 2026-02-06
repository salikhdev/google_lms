package uz.salikhdev.google_lms.domain.dto.request;

public record HomeworkSubmitCheckRequest(
        Long homeworkSubmitId,
        Long score
) {
}
