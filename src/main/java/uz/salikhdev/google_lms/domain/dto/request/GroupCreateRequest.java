package uz.salikhdev.google_lms.domain.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public record GroupCreateRequest(
        @NotNull(message = "Name is required")
        @NotBlank(message = "Name cannot be blank")
        String name,

        @NotNull(message = "Course ID is required")
        @Min(1)
        Long courseId,

        @NotNull(message = "Capacity is required")
        @Min(1)
        Long capacity,

        @NotNull(message = "Mentor ID is required")
        @Min(1)
        Long mentorId,

        @NotNull(message = "Start time is required")
        LocalTime startTime,

        @NotNull(message = "End time is required")
        LocalTime endTime,

        @NotNull(message = "Days of week are required")
        Set<DayOfWeek> daysOfWeek,

        @NotNull(message = "Start date is required")
        LocalDate startDate
) {
}
