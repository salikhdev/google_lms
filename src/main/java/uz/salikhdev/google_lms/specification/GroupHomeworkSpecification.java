package uz.salikhdev.google_lms.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.salikhdev.google_lms.domain.entity.academic.GroupHomework;
import uz.salikhdev.google_lms.domain.entity.academic.HomeworkSubmit;

import java.time.LocalDateTime;

public class GroupHomeworkSpecification {
    public static Specification<GroupHomework> filterGroupHomeworks(
            Long homeworkId,
            Long groupId,
            Long creatorId,
            Boolean isSubmitted,
            LocalDateTime fromDate,
            LocalDateTime toDate
    ) {
        return (root, query, criteriaBuilder) -> {
            var predicates = criteriaBuilder.conjunction();



            if (fromDate != null) {
                predicates =criteriaBuilder.and(predicates,
                        criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), fromDate));
            }

            if (toDate != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), toDate));
            }

            if (homeworkId != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.equal(root.get("homework").get("id"), homeworkId));
            }
            if (groupId != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.equal(root.get("group").get("id"), groupId));
            }
            if (creatorId != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.equal(root.get("creator").get("id"), creatorId));
            }

            if (isSubmitted != null) {
                predicates =criteriaBuilder.and(predicates,
                        criteriaBuilder.equal(root.get("isSubmitted"), isSubmitted));
            }
            return predicates;
        };
    }
}

