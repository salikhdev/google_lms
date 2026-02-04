package uz.salikhdev.google_lms.domain.dto.response;


import lombok.Builder;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Builder
public record GroupResponse(
        Long id,
        String name,
        Long number,
        Long capacity,
        String status,
        UserInfo mentor,
        CourseInfo course,
        LocalDate startDate,
        LocalTime startTime,
        LocalTime endTime,
        Set<DayOfWeek> daysOfWeek,
        UserInfo createdBy,
        LocalDateTime createdAt
) {
}
