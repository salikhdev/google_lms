package uz.salikhdev.google_lms.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.salikhdev.google_lms.domain.dto.request.GroupStudentHomeWorkCreateRequest;
import uz.salikhdev.google_lms.domain.dto.request.GroupStudentHomeWorkFilterRequest;
import uz.salikhdev.google_lms.domain.dto.response.SuccessResponse;
import uz.salikhdev.google_lms.domain.entity.academic.GroupStudentHomeWork;
import uz.salikhdev.google_lms.domain.entity.user.User;
import uz.salikhdev.google_lms.service.GroupStudentHomeWorkService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/group-student-homeworks")
@RequiredArgsConstructor
@Tag(name = "GroupStudentHomeWork API")
public class GroupStudentHomeWorkController {
    private final GroupStudentHomeWorkService groupStudentHomeWorkService;

    @PostMapping("/submit-homework")
    public ResponseEntity<?> submitHomework(@RequestBody GroupStudentHomeWorkCreateRequest request,
                                            @AuthenticationPrincipal User user) {
        groupStudentHomeWorkService.submitHomework(request, user);
        return ResponseEntity.ok(SuccessResponse.ok("Your homework has been submitted"));
    }


    @GetMapping("/all-homeworks")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> getAllHomeworks(@RequestParam(required = false) String search,
                                             @RequestParam(required = false) Long studentId,
                                             @RequestParam(required = false) Long groupId,
                                             @RequestParam(required = false) Long homeworkId,
                                             @RequestParam(required = false) Long fromScore,
                                             @RequestParam(required = false) Long toScore,
                                             @RequestParam(required = false) GroupStudentHomeWork.Status status
    ) {
        GroupStudentHomeWorkFilterRequest filterRequest = GroupStudentHomeWorkFilterRequest.builder()
                .search(search)
                .studentId(studentId)
                .groupId(groupId)
                .homeWorkId(homeworkId)
                .fromScore(fromScore)
                .toScore(toScore)
                .status(status)
                .build();

        return ResponseEntity.ok(groupStudentHomeWorkService.getAllHomeWork(filterRequest));
    }

    @GetMapping("/all-my-homeworks")
    @Operation(summary = "This api for students to see their homeworks")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> getAllStudentHomeworks(@RequestParam(required = false) String search,
                                             @RequestParam(required = false) Long groupId,
                                             @RequestParam(required = false) Long homeworkId,
                                             @RequestParam(required = false) Long fromScore,
                                             @RequestParam(required = false) Long toScore,
                                             @RequestParam(required = false) GroupStudentHomeWork.Status status,
                                                    @AuthenticationPrincipal User user) {
        GroupStudentHomeWorkFilterRequest filterRequest = GroupStudentHomeWorkFilterRequest.builder()
                .search(search)
                .groupId(groupId)
                .homeWorkId(homeworkId)
                .fromScore(fromScore)
                .toScore(toScore)
                .status(status)
                .build();

        return ResponseEntity.ok(groupStudentHomeWorkService.getAllStudentByHomeWork(filterRequest, user));


    }





   /* @PostMapping("/{studentId}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> checkHomework(@PathVariable Long studentId,
                                            @AuthenticationPrincipal User user) {}
*/




}
