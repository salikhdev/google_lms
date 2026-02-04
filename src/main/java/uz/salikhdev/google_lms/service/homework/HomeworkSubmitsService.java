package uz.salikhdev.google_lms.service.homework;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.salikhdev.google_lms.domain.dto.request.GroupStudentHomeWorkCreateRequest;
import uz.salikhdev.google_lms.domain.dto.request.GroupStudentHomeWorkFilterRequest;
import uz.salikhdev.google_lms.domain.dto.response.HomeworkSubmitResponse;
import uz.salikhdev.google_lms.domain.entity.academic.Group;
import uz.salikhdev.google_lms.domain.entity.academic.Homework;
import uz.salikhdev.google_lms.domain.entity.academic.HomeworkSubmit;
import uz.salikhdev.google_lms.domain.entity.user.User;
import uz.salikhdev.google_lms.exception.BadRequestException;
import uz.salikhdev.google_lms.exception.NotFoundException;
import uz.salikhdev.google_lms.mapper.HomeworkSubmitMapper;
import uz.salikhdev.google_lms.repository.GroupRepository;
import uz.salikhdev.google_lms.repository.GroupStudentHomeWorkRepository;
import uz.salikhdev.google_lms.repository.HomeworkRepository;
import uz.salikhdev.google_lms.repository.ResourceRepository;
import uz.salikhdev.google_lms.specification.GroupStudentHomeWorkSpecification;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeworkSubmitsService {
    private final GroupStudentHomeWorkRepository groupStudentHomeWorkRepository;
    private final HomeworkSubmitMapper homeworkSubmitMapper;
    private final GroupRepository groupRepository;
    private final HomeworkRepository homeworkRepository;
    private final ResourceRepository resourceRepository;


    public void submitHomework(GroupStudentHomeWorkCreateRequest request, User student) {
        if(groupStudentHomeWorkRepository.existsByHomeWorkUrl(request.homeWorkUrl())){
           throw new BadRequestException("You already have submitted this homework");
        }
        Group group= groupRepository.findById(request.groupId())
                .orElseThrow(()-> new NotFoundException("Group not found"));
        Homework homeWork = homeworkRepository.findById(request.homeWorkId())
                .orElseThrow(()-> new NotFoundException("Home work not found"));

        HomeworkSubmit homeworkSubmit = HomeworkSubmit.builder()
                .group(group)
                .student(student)
                .homeWork(homeWork)
                .student(student)
                .description(request.description())
                .status(HomeworkSubmit.Status.PENDING)
                .homeWorkUrl(request.homeWorkUrl())
                .build();

        groupStudentHomeWorkRepository.save(homeworkSubmit);

    }

    public List<HomeworkSubmitResponse> getAllHomeWork(GroupStudentHomeWorkFilterRequest  filter) {
        var specification = GroupStudentHomeWorkSpecification.filterGroupStudentHomeWorks(
                filter.search(),
                filter.homeWorkId(),
                filter.groupId(),
                filter.studentId(),
                filter.fromScore(),
                filter.toScore(),
                filter.status()

        );

        List<HomeworkSubmit> homeWorks = groupStudentHomeWorkRepository.findAll(specification);
        return homeworkSubmitMapper.toResponse(homeWorks);
    }

    public List<HomeworkSubmitResponse> getAllStudentByHomeWork(GroupStudentHomeWorkFilterRequest  filter, User student) {
        var specification = GroupStudentHomeWorkSpecification.filterGroupStudentHomeWorks(
                filter.search(),
                filter.homeWorkId(),
                filter.groupId(),
                student.getId(),
                filter.fromScore(),
                filter.toScore(),
                filter.status()

        );

        List<HomeworkSubmit> homeWorks = groupStudentHomeWorkRepository.findAll(specification);
        return homeworkSubmitMapper.toResponse(homeWorks);
    }
}
