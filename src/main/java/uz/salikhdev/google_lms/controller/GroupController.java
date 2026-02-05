package uz.salikhdev.google_lms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.salikhdev.google_lms.domain.dto.request.*;
import uz.salikhdev.google_lms.domain.dto.response.SuccessResponse;
import uz.salikhdev.google_lms.domain.entity.academic.HomeworkSubmit;
import uz.salikhdev.google_lms.domain.entity.user.User;
import uz.salikhdev.google_lms.service.group.GroupHomeWorkService;
import uz.salikhdev.google_lms.service.group.GroupService;
import uz.salikhdev.google_lms.service.group.GroupStudentsService;
import uz.salikhdev.google_lms.service.homework.HomeworkService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
@Tag(name = "Group API")
public class GroupController {

    private final GroupService groupService;
    private final GroupHomeWorkService groupHomeWorkService;
    private final HomeworkService homeworkService;
    private final GroupStudentsService groupStudentsService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<SuccessResponse> createGroup(
            @RequestBody GroupCreateRequest request,
            @AuthenticationPrincipal User authUser
    ) {
        groupService.createGroup(request, authUser);
        return ResponseEntity.ok(SuccessResponse.ok("Group created successfully"));
    }

    @PostMapping("/{groupId}/join")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<SuccessResponse> createGroupStudents(
            @PathVariable Long groupId,
            @RequestBody GroupStudentsRequest request
    ) {
        groupStudentsService.join(groupId, request);
        return ResponseEntity.ok(SuccessResponse.ok("The group students have been successfully joined!"));
    }

    @GetMapping("/{groupId}/students")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public ResponseEntity<?> getGroupStudents(@PathVariable Long groupId) {
        return ResponseEntity.ok(groupStudentsService.getStudents(groupId));

    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CEO')")
    public ResponseEntity<?> allGroups() {
        return ResponseEntity.ok(groupService.getAllGroups());
    }

    @PatchMapping("/{groupId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CEO')")
    public ResponseEntity<SuccessResponse> updateGroup(@PathVariable Long groupId,
                                                       @RequestBody GroupUpdateRequest request
    ) {
        groupService.update(groupId, request);
        return ResponseEntity.ok(SuccessResponse.ok("Group updated successfully"));
    }

    @DeleteMapping("/{groupId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> deleteCourse(@PathVariable Long groupId) {
        groupService.delete(groupId);
        return ResponseEntity.ok(SuccessResponse.ok("Group deleted successfully"));
    }

    @PostMapping("/{groupId}/homeworks")
    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    public ResponseEntity<?> attachHomeWork(@PathVariable Long groupId,
                                            @RequestBody GroupHomeWorkAttachRequest request,
                                            @AuthenticationPrincipal User user) {
        homeworkService.attachHomeworkToGroup(user, groupId, request);
        return ResponseEntity.ok(SuccessResponse.ok("Homework attached to the group successfully"));
    }

    @GetMapping("/{groupId}/homeworks")
    @PreAuthorize("hasAnyRole('TEACHER','ADMIN','STUDENT')")
    public ResponseEntity<?> getGroupHomeworks(@PathVariable Long groupId) {
        return ResponseEntity.ok(homeworkService.getAllAttachHomeworks(groupId));
    }


    @GetMapping("/attached-homeworks")
    public ResponseEntity<?> getAllAttachedHomeworks(
                                                     @RequestParam(required = false) Long creatorId,
                                                     @RequestParam(required = false) Long groupId,
                                                     @RequestParam(required = false) Long homeworkId,
                                                     @RequestParam(required = false) String fromDate,
                                                     @RequestParam(required = false) String toDate,
                                                     @RequestParam(required = false) Boolean isSubmitted
                                                  ) {
        GroupHomeworkFilterRequest filterRequest = GroupHomeworkFilterRequest.builder()
                .groupId(groupId)
                .creatorId(creatorId)
                .homeworkId(homeworkId)
                .fromDate(fromDate != null ? LocalDateTime.parse(fromDate) : null)
                .toDate(toDate != null ? LocalDateTime.parse(toDate) : null)
                .isSubmitted(isSubmitted)
                .build();
        return ResponseEntity.ok(groupHomeWorkService.getAllForTeachers(filterRequest));
    }
}
