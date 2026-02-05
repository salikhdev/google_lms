package uz.salikhdev.google_lms.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.salikhdev.google_lms.domain.dto.request.*;
import uz.salikhdev.google_lms.domain.dto.response.LoginResponse;
import uz.salikhdev.google_lms.domain.entity.user.User;
import uz.salikhdev.google_lms.domain.entity.user.User.Role;
import uz.salikhdev.google_lms.exception.BadRequestException;
import uz.salikhdev.google_lms.exception.ConflictException;
import uz.salikhdev.google_lms.exception.NotFoundException;
import uz.salikhdev.google_lms.mapper.UserMapper;
import uz.salikhdev.google_lms.repository.UserRepository;
import uz.salikhdev.google_lms.service.security.JwtService;
import uz.salikhdev.google_lms.specification.UserSpecification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;


    public void createStudent(UserCreateRequest request) {
        createUser(request, Role.STUDENT);
    }

    public void createTeacher(UserCreateRequest request) {
        createUser(request, Role.TEACHER);
    }

    public void createAdmin(UserCreateRequest request) {
        createUser(request, Role.ADMIN);
    }

    public void createCashier(UserCreateRequest request) {
        createUser(request, Role.CASHIER);
    }

    private void createUser(UserCreateRequest request, Role role) {

        if (userRepository.existsByEmail(request.email())) {
            throw new ConflictException("Email already in use");
        }

        User user = User.builder()
                .email(request.email())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .birthDate(request.birthDate())
                .password(encoder.encode(request.password()))
                .role(role)
                .status(User.Status.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
    }

    public void updateUser(User authUser, Long userId, UserUpdateRequest userRequest) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ConflictException("User not found"));

        // role based restrictions
        if (authUser.getRole().equals(Role.CEO)) {
            if (user.getRole().equals(Role.TEACHER) || user.getRole().equals(Role.STUDENT)) {
                throw new BadRequestException("CEO cannot update TEACHER or STUDENT");
            }
        } else if (authUser.getRole().equals(Role.ADMIN)) {
            if (user.getRole().equals(Role.CEO)) {
                throw new BadRequestException("ADMIN cannot update CEO");
            }
        }


        // update fields
        if (userRequest.email() != null) {
            if (userRepository.existsByEmail(userRequest.email())) {
                throw new ConflictException("Email already in use");
            }
            user.setEmail(userRequest.email());
        }

        if (userRequest.firstName() != null) {
            user.setFirstName(userRequest.firstName());
        }

        if (userRequest.lastName() != null) {
            user.setLastName(userRequest.lastName());
        }

        if (userRequest.birthDate() != null) {
            user.setBirthDate(userRequest.birthDate());
        }

        userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!encoder.matches(request.password(), user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }

        if (user.getStatus() != User.Status.ACTIVE) {
            throw new BadRequestException("User is not active");
        }
//--------------------------------------------------------
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId().toString());
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_" + user.getRole().name());

        // 👑 SUPER USER bo‘lsa — ham role beramiz
        if (user.getRole().name().equals("SUPER_USER")) {
            roles = List.of(
                    "ROLE_SUPER_USER",
                    "ROLE_ADMIN",
                    "ROLE_TEACHER",
                    "ROLE_STUDENT",
                    "ROLE_CASHIER",
                    "ROLE_CE0"
            );
        }


        claims.put("roles", roles); // ✅ LIST bo‘lib ketadi
        //-----------------------------------------------------------

        String token = jwtService.generateToken(claims, user);

        return new LoginResponse(token, jwtService.getExpirationTime());
    }


    public List<UserResponse> getAllUsers(UserFilterRequest filter) {
        var specification = UserSpecification.filterUsers(
                filter.search(),
                filter.userId(),
                filter.status(),
                filter.role(),
                filter.fromDate(),
                filter.toDate()
                );


        List<User> users = userRepository.findAll(specification);
        return userMapper.toResponse(users);

    }
}
