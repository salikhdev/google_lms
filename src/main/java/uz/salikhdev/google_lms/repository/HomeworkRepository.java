package uz.salikhdev.google_lms.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.salikhdev.google_lms.domain.entity.academic.HomeWork;

@Repository
public interface HomeworkRepository extends JpaRepository<HomeWork, Long> {
    boolean existsByTitle(String title);
}
