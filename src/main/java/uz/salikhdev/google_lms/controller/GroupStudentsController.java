package uz.salikhdev.google_lms.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.salikhdev.google_lms.domain.dto.request.GroupStudentsRequest;
import uz.salikhdev.google_lms.domain.dto.response.SuccessResponse;
import uz.salikhdev.google_lms.service.GroupStudentsService;

@RestController
@RequestMapping("/api/group-students")
@RequiredArgsConstructor
@Tag(name = "Group students API")
public class GroupStudentsController {

    private final GroupStudentsService groupStudentsService;

    @PostMapping("/join")
    @PreAuthorize("hasAnyRole('SUPER_USER','ADMIN')")
    public ResponseEntity<SuccessResponse> createGroupStudents(@RequestBody GroupStudentsRequest request) {
        groupStudentsService.join(request);
        return ResponseEntity.ok(SuccessResponse.ok("The group students have been successfully joined!"));
    }

    @GetMapping("/students/{groupId}")
    @PreAuthorize("hasAnyRole('SUPER_USER','ADMIN','TEACHER')")
    public ResponseEntity<?> getGroupStudents(@PathVariable Long groupId) {
     return ResponseEntity.ok(groupStudentsService.getStudents(groupId));

    }
    @GetMapping("/groups/{studentId}")
    @PreAuthorize("hasAnyRole('SUPER_USER','ADMIN')")
    public ResponseEntity<?> getGroups(@PathVariable Long studentId) {
        return ResponseEntity.ok(groupStudentsService.getGroups(studentId));
    }
}
