package uz.salikhdev.google_lms.domain.dto.request;


import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SendNotificationForHomeWorkRequest(
        String groupName,
        String firstName,
        String lastName,
        LocalDateTime deadline,
        String teacherName,
        String homeWorkTitle

) {

}
