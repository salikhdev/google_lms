package uz.salikhdev.google_lms.repository;


import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import uz.salikhdev.google_lms.domain.entity.academic.HomeworkSubmit;

import java.util.List;
import java.util.Optional;

@Repository
public interface HomeworkSubmitRepository extends JpaRepository<HomeworkSubmit, Long>, JpaSpecificationExecutor<HomeworkSubmit> {
    List<HomeworkSubmit> findByGroup_Id(Long  groupId);

    List<HomeworkSubmit> findAllByGroup_Id(Long groupId);

    boolean existsByHomeWorkUrl( String s);

    @Override
    @NonNull
    List<HomeworkSubmit> findAll(Specification<HomeworkSubmit> spec);

    @Override
    Optional<HomeworkSubmit> findById(Long id);





}
