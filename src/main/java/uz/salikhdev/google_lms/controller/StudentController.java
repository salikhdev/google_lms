package uz.salikhdev.google_lms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.salikhdev.google_lms.service.homework.HomeworkSubmitsService;
import uz.salikhdev.google_lms.service.group.GroupStudentsService;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Tag(name = "Student API")
public class StudentController {

    private final GroupStudentsService groupStudentsService;
    private final HomeworkSubmitsService homeworkSubmitsService;

    @GetMapping("/{studentId}/groups")
    @PreAuthorize("hasAnyRole('SUPER_USER','ADMIN')")
    public ResponseEntity<?> getGroups(@PathVariable Long studentId) {
        return ResponseEntity.ok(groupStudentsService.getGroups(studentId));
    }

    /*@GetMapping("/{studentId}/submit-homeworks")
    public ResponseEntity<?> getSubmittedHomeworks(@PathVariable Long studentId) {
        return ResponseEntity.ok(groupStudentsService.getSubmittedHomeworks(studentId));
    }*/
}