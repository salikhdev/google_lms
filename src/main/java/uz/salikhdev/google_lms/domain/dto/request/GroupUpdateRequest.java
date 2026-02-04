package uz.salikhdev.google_lms.domain.dto.request;


import lombok.Builder;
import uz.salikhdev.google_lms.domain.entity.academic.Group;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

@Builder
public record GroupUpdateRequest(
        String name,
        Long mentorId,
        LocalTime startTime,
        LocalTime endTime,
        Group.Status status,
        Set<DayOfWeek> daysOfWeek
) {
}
