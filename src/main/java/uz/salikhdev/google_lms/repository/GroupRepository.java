package uz.salikhdev.google_lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import uz.salikhdev.google_lms.domain.entity.academic.Group;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long>, JpaSpecificationExecutor<Group> {

    boolean existsByNumber(Long number);


    List<Group> findByStartDateBefore(LocalDateTime now);
}
