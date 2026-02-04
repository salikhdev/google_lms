package uz.salikhdev.google_lms.domain.entity.academic;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import uz.salikhdev.google_lms.domain.entity.base.BaseEntity;
import uz.salikhdev.google_lms.domain.entity.user.User;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "groups")
public class Group extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "number", nullable = false, unique = true)
    private Long number;

    @Column(name = "capacity", nullable = false)
    private Long capacity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "mentor_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "fk_group_mentor",
                    foreignKeyDefinition = "FOREIGN KEY (mentor_id) REFERENCES users(id) ON SET NULL"
            )
    )
    @ToString.Exclude
    private User mentor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "course_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "fk_group_course",
                    foreignKeyDefinition = "FOREIGN KEY (course_id) REFERENCES courses(id) ON SET NULL"
            )
    )
    @ToString.Exclude
    private Course course;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "schedule_days",
            joinColumns = @JoinColumn(name = "group_id")
    )
    @Column(name = "day_of_week")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<DayOfWeek> daysOfWeek = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "created_by",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "fk_group_created_by",
                    foreignKeyDefinition = "FOREIGN KEY (created_by) REFERENCES users(id) ON SET NULL"
            )
    )
    @ToString.Exclude
    private User createdBy;

    public enum Status {
        DRAFT,
        ACTIVE,
        FINISHED
    }
}
