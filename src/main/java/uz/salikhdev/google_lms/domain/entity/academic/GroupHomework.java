package uz.salikhdev.google_lms.domain.entity.academic;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import uz.salikhdev.google_lms.domain.entity.base.BaseEntity;
import uz.salikhdev.google_lms.domain.entity.user.User;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "group_homework")
public class GroupHomework extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    @ToString.Exclude
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "homework_id", nullable = false)
    @ToString.Exclude
    private Homework homework;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    @ToString.Exclude
    private User creator;

    private LocalDateTime deadline;

    private Boolean isSubmitted;

}
