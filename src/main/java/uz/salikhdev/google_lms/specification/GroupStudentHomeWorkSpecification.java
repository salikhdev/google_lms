package uz.salikhdev.google_lms.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.salikhdev.google_lms.domain.entity.academic.GroupStudentHomeWork;
import uz.salikhdev.google_lms.domain.entity.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class GroupStudentHomeWorkSpecification {
    public static Specification<GroupStudentHomeWork> filterGroupStudentHomeWorks(
            String search,
            Long homeWorkId,
            Long groupId,
            Long studentId,
            Long fromScore,
            Long toScore,
            GroupStudentHomeWork.Status status
    ) {
        return (root, query, criteriaBuilder) -> {
            var predicates = criteriaBuilder.conjunction();





            if(search!=null && !search.trim().isEmpty()){

                String searchPattern = "%"+search.trim().toLowerCase()+ "%";
                var searchPredicate = criteriaBuilder.or(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("description")),
                                searchPattern
                        ),
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("homeWorkUrl")),
                                searchPattern
                        )
                );

                predicates = criteriaBuilder.and(predicates, searchPredicate);
            }

            // STATUS
            if (status != null) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.equal(root.get("status"), status));
            }


            if (fromScore != null) {
                predicates =criteriaBuilder.and(predicates,
                        criteriaBuilder.greaterThanOrEqualTo(root.get("score"), fromScore));
            }

            if (toScore != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.lessThanOrEqualTo(root.get("score"), toScore));
            }

            if (homeWorkId != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.equal(root.get("homeWork").get("id"), homeWorkId));
            }
            if (groupId != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.equal(root.get("group").get("id"), groupId));
            }
            if (studentId != null) {
                predicates = criteriaBuilder.and(predicates,
                        criteriaBuilder.equal(root.get("student").get("id"), studentId));
            }



            return predicates;
        };
    }
}



