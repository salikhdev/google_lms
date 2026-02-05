package uz.salikhdev.google_lms.service.course;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.salikhdev.google_lms.domain.dto.request.CourseRequest;
import uz.salikhdev.google_lms.domain.dto.request.CourseUpdateRequest;
import uz.salikhdev.google_lms.domain.dto.response.CourseResponse;
import uz.salikhdev.google_lms.domain.entity.academic.Course;
import uz.salikhdev.google_lms.exception.BadRequestException;
import uz.salikhdev.google_lms.exception.NotFoundException;
import uz.salikhdev.google_lms.mapper.CourseMapper;
import uz.salikhdev.google_lms.repository.CourseRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public void create(CourseRequest request) {
        if(courseRepository.existsByName(request.name())){
            throw new BadRequestException("Course already exists");
        }

        Course course = Course.builder()
                .name(request.name())
                .price(request.price())
                .build();
        courseRepository.save(course);
    }

    public List<CourseResponse> getAll(){
        List<Course> courses=courseRepository.findAll();
        return courseMapper.toResponse(courses);
    }

    public void update(Long courseId, CourseUpdateRequest request) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found"));

        if (request.name() != null && !request.name().isBlank()) {
            course.setName(request.name());
        }

        if (request.price() != null) {
            course.setPrice(request.price());
        }

        courseRepository.save(course);
    }

    public void delete(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new NotFoundException("Course not found"));
        courseRepository.delete(course);
    }
}
