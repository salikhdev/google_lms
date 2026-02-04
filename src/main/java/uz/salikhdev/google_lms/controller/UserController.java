package uz.salikhdev.google_lms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.salikhdev.google_lms.domain.dto.request.UserCreateRequest;
import uz.salikhdev.google_lms.domain.dto.request.LoginRequest;
import uz.salikhdev.google_lms.domain.dto.request.UpdateUserRequest;
import uz.salikhdev.google_lms.domain.dto.request.UserFilterRequest;
import uz.salikhdev.google_lms.domain.dto.response.LoginResponse;
import uz.salikhdev.google_lms.domain.dto.response.SuccessResponse;
import uz.salikhdev.google_lms.domain.entity.user.User;
import uz.salikhdev.google_lms.service.user.UserService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User API")
public class UserController {

    private final UserService userService;

    @PostMapping("/create-admin")
    @PreAuthorize("hasAnyRole('SUPER_USER','CEO')")
    public ResponseEntity<SuccessResponse> createAdmin(@RequestBody UserCreateRequest userRequest) {
        userService.createAdmin(userRequest);
        return ResponseEntity.ok(SuccessResponse.ok("Admin created successfully"));
    }

    @PostMapping("/create-cashier")
    @PreAuthorize("hasAnyRole('SUPER_USER','CEO')")
    public ResponseEntity<SuccessResponse> createCashier(@RequestBody UserCreateRequest userRequest) {
        userService.createCashier(userRequest);
        return ResponseEntity.ok(SuccessResponse.ok("Cashier created successfully"));
    }

    @PostMapping("/create-teacher")
    @PreAuthorize("hasAnyRole('SUPER_USER','ADMIN')")
    public ResponseEntity<SuccessResponse> createTeacher(@RequestBody UserCreateRequest userRequest) {
        userService.createTeacher(userRequest);
        return ResponseEntity.ok(SuccessResponse.ok("Teacher created successfully"));
    }

    @PostMapping("/create-student")
    @PreAuthorize("hasAnyRole('SUPER_USER','ADMIN','CEO')")
    public ResponseEntity<SuccessResponse> createStudent(@RequestBody UserCreateRequest userRequest) {
        userService.createStudent(userRequest);
        return ResponseEntity.ok(SuccessResponse.ok("Student created successfully"));
    }

    @PatchMapping("/{userId}")
    @PreAuthorize("hasAnyRole('SUPER_USER','ADMIN', 'CEO')")
    public ResponseEntity<SuccessResponse> updateStudent(
            @PathVariable("userId") Long userid,
            @RequestBody UpdateUserRequest userRequest,
            @AuthenticationPrincipal User authUser
    ) {
        userService.updateUser(authUser, userid, userRequest);
        return ResponseEntity.ok(SuccessResponse.ok("User updated successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = userService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('SUPER_USER','ADMIN', 'CEO')")
    public ResponseEntity<?> allUsers(@RequestParam(required = false) String search,
                                      @RequestParam(required = false) User.Status status,
                                      @RequestParam(required = false) User.Role role,
                                      @RequestParam(required = false) String fromDate,
                                      @RequestParam(required = false) String toDate,
                                      @RequestParam(required = false) Long userId) {
        UserFilterRequest filterRequest = UserFilterRequest.builder()
                .search(search)
                .status(status)
                .role(role)
                .fromDate(fromDate != null ? LocalDate.parse(fromDate) : null)
                .toDate(toDate != null ? LocalDate.parse(toDate) : null)
                .userId(userId)
                .build();

        return ResponseEntity.ok(userService.getAllUsers(filterRequest));


    }
}
