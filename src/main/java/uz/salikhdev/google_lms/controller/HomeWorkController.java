package uz.salikhdev.google_lms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.salikhdev.google_lms.domain.dto.request.HomeworkCreateRequest;
import uz.salikhdev.google_lms.domain.dto.request.UpdateHomeWorkRequest;
import uz.salikhdev.google_lms.domain.dto.response.SuccessResponse;
import uz.salikhdev.google_lms.service.HomeworkService;

@RestController
@RequestMapping("/api/homeworks")
@RequiredArgsConstructor
@Tag(name = "HomeWork API")
public class HomeWorkController {
    private final HomeworkService homeworkService;

    @PostMapping
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> createHomeWork(@RequestBody HomeworkCreateRequest request) {
        homeworkService.createHomework(request);
        return ResponseEntity.ok(SuccessResponse.ok("Homework has been created"));
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
