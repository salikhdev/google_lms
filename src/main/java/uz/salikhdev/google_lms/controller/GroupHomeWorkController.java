package uz.salikhdev.google_lms.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.salikhdev.google_lms.domain.dto.request.GroupHomeWorkAttachRequest;
import uz.salikhdev.google_lms.domain.dto.response.SuccessResponse;
import uz.salikhdev.google_lms.domain.entity.user.User;
import uz.salikhdev.google_lms.service.HomeworkService;

@RestController
@RequestMapping("/api/group-homeworks")
@RequiredArgsConstructor
@Tag(name = "GroupHomeWork API")
public class GroupHomeWorkController {
    private final HomeworkService homeworkService;

    @PostMapping("/attachHomeWork")
    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    public ResponseEntity<?> attachHomeWork(@RequestBody GroupHomeWorkAttachRequest request,
                                            @AuthenticationPrincipal User user) {
        homeworkService.attachHomeworkToGroup(user, request);
        return ResponseEntity.ok(SuccessResponse.ok("Homework attached to the group successfully"));
    }

    @PostMapping("/sendHomeWork/{groupHomeWorkId}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> attachHomeWork( @PathVariable Long groupHomeWorkId,
                                            @AuthenticationPrincipal User teacher) {
        homeworkService.sendHomeWorkToGroup(teacher, groupHomeWorkId);
        return ResponseEntity.ok(SuccessResponse.ok("Homework have sent to the group successfully"));
    }
}
