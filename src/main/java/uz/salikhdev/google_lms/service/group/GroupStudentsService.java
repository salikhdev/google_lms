package uz.salikhdev.google_lms.service.group;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.salikhdev.google_lms.domain.dto.request.GroupStudentsRequest;
import uz.salikhdev.google_lms.domain.dto.request.UserResponse;
import uz.salikhdev.google_lms.domain.dto.response.GroupResponse;
import uz.salikhdev.google_lms.domain.entity.academic.Group;
import uz.salikhdev.google_lms.domain.entity.academic.GroupStudent;
import uz.salikhdev.google_lms.domain.entity.user.User;
import uz.salikhdev.google_lms.exception.BadRequestException;
import uz.salikhdev.google_lms.exception.NotFoundException;
import uz.salikhdev.google_lms.mapper.GroupMapper;
import uz.salikhdev.google_lms.mapper.UserMapper;
import uz.salikhdev.google_lms.repository.GroupRepository;
import uz.salikhdev.google_lms.repository.GroupStudentsRepository;
import uz.salikhdev.google_lms.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupStudentsService {

    private final GroupStudentsRepository groupStudentsRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final UserMapper userMapper;
    private final GroupMapper groupMapper;

    public void join(Long groupId, GroupStudentsRequest request) {

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group not found"));

        if (group.getStatus() != Group.Status.ACTIVE) {
            throw new BadRequestException("Cannot join inactive group");
        }

        if (group.getCapacity() <= 0) {
            throw new BadRequestException("Group capacity is full");
        }

        User student = userRepository.findByIdAndStatus(request.studentId(), User.Status.ACTIVE)
                .orElseThrow(()-> new NotFoundException("Student not found"));

        if(student.getRole() != User.Role.STUDENT){
            throw new BadRequestException("Only student can join");
        }

        GroupStudent groupStudents = GroupStudent.builder()
                .student(student)
                .group(group)
                .joinedAt(LocalDate.now())
                .status(GroupStudent.Status.ACTIVE)
                .build();
        groupStudentsRepository.save(groupStudents);
        group.setCapacity(group.getCapacity() - 1);
        groupRepository.save(group);
    }

    public List<UserResponse> getStudents(Long groupId) {
        List<GroupStudent> groupStudents = groupStudentsRepository.findAllByGroup_Id(groupId);
        return userMapper.toResponse(
                groupStudents.stream()
                        .map(GroupStudent::getStudent)
                        .toList()
        );
    }

    public List<GroupResponse> getGroups(Long studentId) {
        List<GroupStudent> groupStudents = groupStudentsRepository.findAllByStudent_Id(studentId);

        return groupMapper.toResponse(
                groupStudents.stream()
                        .map(GroupStudent::getGroup)
                        .toList()
        );
    }
}
