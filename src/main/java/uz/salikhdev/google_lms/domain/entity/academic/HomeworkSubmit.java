package uz.salikhdev.google_lms.domain.entity.academic;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import uz.salikhdev.google_lms.domain.entity.base.BaseEntity;
import uz.salikhdev.google_lms.domain.entity.user.User;

@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "homework_submit")
public class HomeworkSubmit extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    @ToString.Exclude
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_work_id", nullable = false)
    @ToString.Exclude
    private Homework homeWork;

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
