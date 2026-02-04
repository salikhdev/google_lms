package uz.salikhdev.google_lms.repository;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.salikhdev.google_lms.domain.entity.academic.GroupHomework;

import java.util.List;

@Repository
public interface GroupHomeworkRepository extends JpaRepository<GroupHomework, Long> {

    boolean existsByHomeworkId(Long homeworkId);

    @Override
    @EntityGraph(attributePaths = {"homework", "group"})
    <S extends GroupHomework> S save(S entity);

    @EntityGraph(attributePaths = {"homework"})
    List<GroupHomework> findByGroup_Id(Long groupId);
}
