package uz.salikhdev.google_lms.specification;

import org.springframework.data.jpa.domain.Specification;
import uz.salikhdev.google_lms.domain.entity.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserSpecification {

        public static Specification<User> filterUsers(
                String search,
                Long userId,
                User.Status status,
                User.Role role,
                LocalDate fromDate,
                LocalDate toDate

        ) {
            return (root, query, criteriaBuilder) -> {
                var predicates = criteriaBuilder.conjunction();




                if(search!=null && !search.trim().isEmpty()){

                    String searchPattern = "%"+search.trim().toLowerCase()+ "%";
                    var searchPredicate = criteriaBuilder.or(
                            criteriaBuilder.like(
                                    criteriaBuilder.lower(root.get("email")),
                                    searchPattern
                            ),
                            criteriaBuilder.like(
                                    criteriaBuilder.lower(root.get("firstName")),
                                    searchPattern
                            ),
                            criteriaBuilder.like(
                                    criteriaBuilder.lower(root.get("lastName")),
                                    searchPattern
                            )
                    );

                    predicates = criteriaBuilder.and(predicates, searchPredicate);
                }

                // STATUS
                if (status != null) {
                    predicates = criteriaBuilder.and(predicates, criteriaBuilder.equal(root.get("code"), status));
                }

                // TYPE
                if (role != null) {
                    predicates =criteriaBuilder.and(predicates, criteriaBuilder.equal(root.get("role"), role));
                }

                // DATE RANGE
                if (fromDate != null) {
                    predicates =criteriaBuilder.and(predicates,
                            criteriaBuilder.greaterThanOrEqualTo(root.get("birthDate"), fromDate));
                }

                if (toDate != null) {
                    predicates = criteriaBuilder.and(predicates,
                            criteriaBuilder.lessThanOrEqualTo(root.get("birthDate"), toDate));
                }

                if (userId != null) {
                    predicates = criteriaBuilder.and(predicates,
                            criteriaBuilder.equal(root.get("user").get("id"), userId));
                }



                return predicates;
            };
        }
    }

