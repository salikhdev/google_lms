package uz.salikhdev.google_lms.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.salikhdev.google_lms.domain.entity.academic.Homework;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, Long> {
    boolean existsByTitle(String title);


    List<Homework> findByCreatedAtBeforeAndStatus(LocalDateTime threeHoursAgo, Homework.Status status);
}
