package uz.salikhdev.google_lms.repository;


import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import uz.salikhdev.google_lms.domain.entity.academic.GroupStudentHomeWork;
import uz.salikhdev.google_lms.domain.entity.user.User;

import java.util.List;

@Repository
public interface GroupStudentHomeWorkRepository extends JpaRepository<GroupStudentHomeWork, Long>, JpaSpecificationExecutor<GroupStudentHomeWork> {
    List<GroupStudentHomeWork> findByGroup_Id(Long  groupId);

    List<GroupStudentHomeWork> findAllByGroup_Id(Long groupId);

    boolean existsByHomeWorkUrl( String s);

    @Override
    @NonNull
    List<GroupStudentHomeWork> findAll(Specification<GroupStudentHomeWork> spec);
}
