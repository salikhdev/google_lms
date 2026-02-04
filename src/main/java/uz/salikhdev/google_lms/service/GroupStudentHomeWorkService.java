package uz.salikhdev.google_lms.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import uz.salikhdev.google_lms.domain.dto.request.GroupStudentHomeWorkCreateRequest;
import uz.salikhdev.google_lms.domain.dto.request.GroupStudentHomeWorkFilterRequest;
import uz.salikhdev.google_lms.domain.dto.response.GroupStudentHomeWorkResponse;
import uz.salikhdev.google_lms.domain.entity.academic.Group;
import uz.salikhdev.google_lms.domain.entity.academic.GroupStudentHomeWork;
import uz.salikhdev.google_lms.domain.entity.academic.HomeWork;
import uz.salikhdev.google_lms.domain.entity.resource.Resource;
import uz.salikhdev.google_lms.domain.entity.user.User;
import uz.salikhdev.google_lms.exception.BadRequestException;
import uz.salikhdev.google_lms.exception.NotFoundException;
import uz.salikhdev.google_lms.mapper.GroupStudentHomeWorkMapper;
import uz.salikhdev.google_lms.repository.*;
import uz.salikhdev.google_lms.specification.GroupStudentHomeWorkSpecification;
import uz.salikhdev.google_lms.specification.UserSpecification;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupStudentHomeWorkService {
    private final GroupStudentHomeWorkRepository groupStudentHomeWorkRepository;
    private final GroupStudentHomeWorkMapper  groupStudentHomeWorkMapper;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final HomeworkRepository homeworkRepository;
    private final ResourceRepository resourceRepository;


    public void submitHomework(GroupStudentHomeWorkCreateRequest request, User student) {
        if(groupStudentHomeWorkRepository.existsByHomeWorkUrl(request.homeWorkUrl())){
           throw new BadRequestException("You already have submitted this homework");
        }
        Group group= groupRepository.findById(request.groupId())
                .orElseThrow(()-> new NotFoundException("Group not found"));
        HomeWork homeWork= homeworkRepository.findById(request.homeWorkId())
                .orElseThrow(()-> new NotFoundException("Home work not found"));
        Resource resource =resourceRepository.findByUrlAndStatusNot(request.homeWorkUrl(), Resource.Status.DELETED)
                .orElseThrow(() -> new NotFoundException("Resource not found"));

        GroupStudentHomeWork  groupStudentHomeWork = GroupStudentHomeWork.builder()
                .group(group)
                .student(student)
                .homeWork(homeWork)
                .student(student)
                .description(request.description())
                .status(GroupStudentHomeWork.Status.PENDING)
                .homeWorkUrl(request.homeWorkUrl())
                .build();

        groupStudentHomeWorkRepository.save(groupStudentHomeWork);

    }

    public List<GroupStudentHomeWorkResponse> getAllHomeWork(GroupStudentHomeWorkFilterRequest  filter) {
        var specification = GroupStudentHomeWorkSpecification.filterGroupStudentHomeWorks(
                filter.search(),
                filter.homeWorkId(),
                filter.groupId(),
                filter.studentId(),
                filter.fromScore(),
                filter.toScore(),
                filter.status()

        );

        List<GroupStudentHomeWork> homeWorks = groupStudentHomeWorkRepository.findAll(specification);
        return groupStudentHomeWorkMapper.toResponse(homeWorks);
    }

    public List<GroupStudentHomeWorkResponse> getAllStudentByHomeWork(GroupStudentHomeWorkFilterRequest  filter, User student) {
        var specification = GroupStudentHomeWorkSpecification.filterGroupStudentHomeWorks(
                filter.search(),
                filter.homeWorkId(),
                filter.groupId(),
                student.getId(),
                filter.fromScore(),
                filter.toScore(),
                filter.status()

        );

        List<GroupStudentHomeWork> homeWorks = groupStudentHomeWorkRepository.findAll(specification);
        return groupStudentHomeWorkMapper.toResponse(homeWorks);
    }
}
