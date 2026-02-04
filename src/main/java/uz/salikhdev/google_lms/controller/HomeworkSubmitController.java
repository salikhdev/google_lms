package uz.salikhdev.google_lms.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.salikhdev.google_lms.domain.dto.request.GroupStudentHomeWorkCreateRequest;
import uz.salikhdev.google_lms.domain.dto.request.GroupStudentHomeWorkFilterRequest;
import uz.salikhdev.google_lms.domain.dto.response.SuccessResponse;
import uz.salikhdev.google_lms.domain.entity.academic.HomeworkSubmit;
import uz.salikhdev.google_lms.domain.entity.user.User;
import uz.salikhdev.google_lms.service.homework.HomeworkSubmitsService;

@RestController
@RequestMapping("/api/homework-submits")
@RequiredArgsConstructor
@Tag(name = "HomeworkSubmit API")
public class HomeworkSubmitController {

    private final HomeworkSubmitsService homeworkSubmitsService;

    @PostMapping("/submit-homework")
    public ResponseEntity<?> submitHomework(@RequestBody GroupStudentHomeWorkCreateRequest request,
                                            @AuthenticationPrincipal User user) {
        homeworkSubmitsService.submitHomework(request, user);
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
                                             @RequestParam(required = false) HomeworkSubmit.Status status
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

        return ResponseEntity.ok(homeworkSubmitsService.getAllHomeWork(filterRequest));
    }

    @GetMapping("/all-my-homeworks")
    @Operation(summary = "This api for students to see their homeworks")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> getAllStudentHomeworks(@RequestParam(required = false) String search,
                                             @RequestParam(required = false) Long groupId,
                                             @RequestParam(required = false) Long homeworkId,
                                             @RequestParam(required = false) Long fromScore,
                                             @RequestParam(required = false) Long toScore,
                                             @RequestParam(required = false) HomeworkSubmit.Status status,
                                                    @AuthenticationPrincipal User user) {
        GroupStudentHomeWorkFilterRequest filterRequest = GroupStudentHomeWorkFilterRequest.builder()
                .search(search)
                .groupId(groupId)
                .homeWorkId(homeworkId)
                .fromScore(fromScore)
                .toScore(toScore)
                .status(status)
                .build();

        return ResponseEntity.ok(homeworkSubmitsService.getAllStudentByHomeWork(filterRequest, user));
    }





   /* @PostMapping("/{studentId}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> checkHomework(@PathVariable Long studentId,
                                            @AuthenticationPrincipal User user) {}
*/




}
