package uz.salikhdev.google_lms.repository;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.salikhdev.google_lms.domain.entity.academic.GroupStudent;

import java.util.List;

@Repository
public interface GroupStudentsRepository extends JpaRepository<GroupStudent, Long> {

    @EntityGraph(attributePaths = {"student"})
    List<GroupStudent> findAllByGroup_Id(Long groupId);

    @EntityGraph(attributePaths = {"group"})
    List<GroupStudent> findAllByStudent_Id(Long studentId);

}
