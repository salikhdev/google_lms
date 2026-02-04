package uz.salikhdev.google_lms.domain.entity.academic;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import uz.salikhdev.google_lms.domain.entity.base.BaseEntity;
import uz.salikhdev.google_lms.domain.entity.user.User;

@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "group_student_homework")
public class GroupStudentHomeWork extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    @ToString.Exclude
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_work_id", nullable = false)
    @ToString.Exclude
    private HomeWork homework;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    @ToString.Exclude
    private User student;

    @Column(name = "description")
    String description;

    @Column(name = "homework_url")
    String homeWorkUrl;

    @Column(name = "score")
    Long score;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    Status status;

    public enum Status {
        PENDING ,
        CHECKED
    }
}
