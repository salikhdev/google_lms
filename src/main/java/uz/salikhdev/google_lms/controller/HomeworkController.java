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
import uz.salikhdev.google_lms.domain.dto.request.HomeworkCreateRequest;
import uz.salikhdev.google_lms.domain.dto.request.UpdateHomeWorkRequest;
import uz.salikhdev.google_lms.domain.dto.response.HomeworkResponse;
import uz.salikhdev.google_lms.domain.dto.response.SuccessResponse;
import uz.salikhdev.google_lms.service.homework.HomeworkService;

import java.util.List;

@RestController
@RequestMapping("/api/homeworks")
@RequiredArgsConstructor
@Tag(name = "Homework API")
public class HomeworkController {
    private final HomeworkService homeworkService;

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_USER','TEACHER')")
    public ResponseEntity<?> createHomeWork(@RequestBody HomeworkCreateRequest request) {
        homeworkService.createHomework(request);
        return ResponseEntity.ok(SuccessResponse.ok("Homework has been created"));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_USER','ADMIN', 'TEACHER', 'STUDENT')")
    public ResponseEntity<List<HomeworkResponse>> getAllHomeWorks() {
        return ResponseEntity.ok(homeworkService.getAllHomeworks());
    }

    @DeleteMapping("/{homeWorkId}")
    @PreAuthorize("hasAnyRole('SUPER_USER','ADMIN', 'TEACHER')")
    public ResponseEntity<?> deleteHomeWork(@PathVariable Long homeWorkId) {
        homeworkService.deleteHomework(homeWorkId);
        return ResponseEntity.ok(SuccessResponse.ok("Homework has been deleted"));
    }


    @PatchMapping("/{homeWorkId}")
    @PreAuthorize("hasAnyRole('SUPER_USER','ADMIN', 'TEACHER')")
    public ResponseEntity<?> edit(@PathVariable Long homeWorkId,
                                  @RequestBody UpdateHomeWorkRequest request){
        homeworkService.update(homeWorkId, request);
        return ResponseEntity.ok(SuccessResponse.ok("Homework has been updated"));

    }

}
