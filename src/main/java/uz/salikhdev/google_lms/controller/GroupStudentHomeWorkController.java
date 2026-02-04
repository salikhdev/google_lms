package uz.salikhdev.google_lms.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.salikhdev.google_lms.domain.dto.request.GroupStudentHomeWorkCreateRequest;
import uz.salikhdev.google_lms.domain.dto.response.SuccessResponse;
import uz.salikhdev.google_lms.domain.entity.academic.GroupStudentHomeWork;
import uz.salikhdev.google_lms.domain.entity.user.User;
import uz.salikhdev.google_lms.service.GroupStudentHomeWorkService;

@RestController
@RequestMapping("/api/group-student-homeworks")
@RequiredArgsConstructor
@Tag(name = "GroupStudentsHomeWork API")
public class GroupStudentHomeWorkController {
    private final GroupStudentHomeWorkService groupStudentHomeWorkService;

    @PostMapping("/submit-homework")
    public ResponseEntity<?> submitHomework(@RequestBody GroupStudentHomeWorkCreateRequest request,
                                            @AuthenticationPrincipal User user) {
        groupStudentHomeWorkService.submitHomework(request, user);
        return ResponseEntity.ok(SuccessResponse.ok("Your homework has been submitted"));
    }





}
