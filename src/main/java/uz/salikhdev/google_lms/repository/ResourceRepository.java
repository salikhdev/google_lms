package uz.salikhdev.google_lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.salikhdev.google_lms.domain.entity.resource.Resource;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    Optional<Resource> findByKey(String key);



    Optional<Resource> findByUrlAndStatusNot(String s, Resource.Status status);

    List<Resource> findByStatus(Resource.Status status);
}
