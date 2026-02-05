package uz.salikhdev.google_lms.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.salikhdev.google_lms.domain.dto.request.CourseRequest;
import uz.salikhdev.google_lms.domain.dto.request.CourseUpdateRequest;
import uz.salikhdev.google_lms.domain.dto.response.SuccessResponse;
import uz.salikhdev.google_lms.service.course.CourseService;


@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@Tag(name = "Course API")
public class CourseController {
    private final CourseService courseService;


    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> createCourse(@RequestBody CourseRequest request) {
        courseService.create(request);
        return ResponseEntity.ok(SuccessResponse.ok("Course created successfully"));
    }

    @GetMapping()
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok( courseService.getAll());
    }

    @PatchMapping("/{courseId}")
    @PreAuthorize("hasAnyRole('SUPER_USER','ADMIN')")
    public ResponseEntity<?> updateCourse(@PathVariable Long courseId, @RequestBody CourseUpdateRequest request){
        courseService.update(courseId,request);
        return ResponseEntity.ok(SuccessResponse.ok("Course updated successfully"));
    }

    @DeleteMapping("/{courseId}")
    @PreAuthorize("hasAnyRole('SUPER_USER','ADMIN')")
    public ResponseEntity<?> deleteCourse(@PathVariable Long courseId){
        courseService.delete(courseId);
        return ResponseEntity.ok(SuccessResponse.ok("Course deleted successfully"));
    }

}
