package uz.salikhdev.google_lms.service.group;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.salikhdev.google_lms.domain.dto.request.GroupCreateRequest;
import uz.salikhdev.google_lms.domain.dto.request.GroupUpdateRequest;
import uz.salikhdev.google_lms.domain.dto.response.GroupResponse;
import uz.salikhdev.google_lms.domain.entity.academic.Course;
import uz.salikhdev.google_lms.domain.entity.academic.Group;
import uz.salikhdev.google_lms.domain.entity.user.User;
import uz.salikhdev.google_lms.exception.NotFoundException;
import uz.salikhdev.google_lms.mapper.GroupMapper;
import uz.salikhdev.google_lms.repository.CourseRepository;
import uz.salikhdev.google_lms.repository.GroupRepository;
import uz.salikhdev.google_lms.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final GroupMapper groupMapper;
    private final Random random;

    public void createGroup(GroupCreateRequest request, User authUser) {

        long groupNumber = random.nextLong(1000, 9999);

        if (groupRepository.existsByNumber(groupNumber)) {
            createGroup(request, authUser);
        }

        Course course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new NotFoundException("Course not found"));

        User teacher = userRepository.findById(request.mentorId())
                .orElseThrow(() -> new NotFoundException("Mentor not found"));

        if (!teacher.getRole().equals(User.Role.TEACHER)) {
            throw new NotFoundException("User is not teacher");
        }

        Group group = Group.builder()
                .name(request.name())
                .number(groupNumber)
                .capacity(request.capacity())
                .course(course)
                .mentor(teacher)
                .startDate(request.startDate())
                .startTime(request.startTime())
                .status(Group.Status.DRAFT)
                .daysOfWeek(request.daysOfWeek())
                .endTime(request.endTime())
                .createdBy(authUser)
                .build();

        if (request.startDate().isBefore(LocalDate.now()) || request.startDate().isEqual(LocalDate.now())) {
            group.setStatus(Group.Status.ACTIVE);
        }

        groupRepository.save(group);
    }

    public List<GroupResponse> getAllGroups() {
        List<Group> groups = groupRepository.findAll();
        return groupMapper.toResponse(groups);
    }

    public void update(Long groupId, GroupUpdateRequest request) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group not found"));

        if (request.mentorId() != null) {
            User teacher = userRepository.findById(request.mentorId())
                    .orElseThrow(() -> new NotFoundException("Mentor not teacher"));
            if (teacher.getRole() != User.Role.TEACHER) {
                throw new NotFoundException("User is not teacher");
            }
            group.setMentor(teacher);
        }

        if (request.startTime() != null) {
            group.setStartTime(request.startTime());
        }

        if (request.endTime() != null) {
            group.setEndTime(request.endTime());
        }

        if (request.name() != null) {
            group.setName(request.name());
        }

        if (request.status() != null) {
            group.setStatus(request.status());
        }

        if (request.daysOfWeek() != null) {
            group.setDaysOfWeek(request.daysOfWeek());
        }

        groupRepository.save(group);
    }

    public void delete(Long groupId) {
        groupRepository.deleteById(groupId);
    }
}
