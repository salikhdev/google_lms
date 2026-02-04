package uz.salikhdev.google_lms.domain.entity.academic;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import uz.salikhdev.google_lms.domain.entity.base.BaseEntity;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "homework")
public class Homework extends BaseEntity {

    @Column(nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(name = "content_url", nullable = false)
    private String contentUrl;
    @Column(name = "max_score", nullable = false)
    private Long maxScore;

}
