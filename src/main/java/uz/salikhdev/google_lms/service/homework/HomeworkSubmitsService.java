package uz.salikhdev.google_lms.service.homework;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.salikhdev.google_lms.domain.dto.request.HomeWorkSubmitCreateRequest;
import uz.salikhdev.google_lms.domain.dto.request.HomeworkSubmitCheckRequest;
import uz.salikhdev.google_lms.domain.dto.request.HomeworkSubmitFilterRequest;
import uz.salikhdev.google_lms.domain.dto.response.HomeworkSubmitResponse;
import uz.salikhdev.google_lms.domain.entity.academic.Group;
import uz.salikhdev.google_lms.domain.entity.academic.GroupHomework;
import uz.salikhdev.google_lms.domain.entity.academic.Homework;
import uz.salikhdev.google_lms.domain.entity.academic.HomeworkSubmit;
import uz.salikhdev.google_lms.domain.entity.user.User;
import uz.salikhdev.google_lms.exception.BadRequestException;
import uz.salikhdev.google_lms.exception.NotFoundException;
import uz.salikhdev.google_lms.mapper.HomeworkSubmitMapper;
import uz.salikhdev.google_lms.repository.*;
import uz.salikhdev.google_lms.specification.HomeWorkSubmitSpecification;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HomeworkSubmitsService {
    private final HomeworkSubmitRepository homeworkSubmitRepository;
    private final HomeworkSubmitMapper homeworkSubmitMapper;
    private final GroupRepository groupRepository;
    private final GroupHomeworkRepository groupHomeworkRepository;
    private final HomeworkRepository homeworkRepository;
    private final ResourceRepository resourceRepository;


    public void submitHomework(HomeWorkSubmitCreateRequest request, User student) {
        if(homeworkSubmitRepository.existsByHomeWorkUrl(request.homeWorkUrl())){
           throw new BadRequestException("You already have submitted this homework");
        }

        //-------------------------------------------------------------------
        GroupHomework groupHomework = groupHomeworkRepository.findByHomework_id(request.homeWorkId())
                .orElseThrow(()-> new NotFoundException("Group homework not found"));
        if(groupHomework.getIsSubmitted().equals(true)){
            throw new BadRequestException("You have already skipped the deadline time");
        }
        //--------------------------------------------------------------------

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

        homeworkSubmitRepository.save(homeworkSubmit);

    }

    public List<HomeworkSubmitResponse> getAllHomeWork(HomeworkSubmitFilterRequest filter) {
        var specification = HomeWorkSubmitSpecification.filterHomeworkSubmit(
                filter.search(),
                filter.homeWorkId(),
                filter.groupId(),
                filter.studentId(),
                filter.fromScore(),
                filter.toScore(),
                filter.status()

        );

        List<HomeworkSubmit> homeworks = homeworkSubmitRepository.findAll(specification);
        return homeworkSubmitMapper.toResponse(homeworks);
    }

    public List<HomeworkSubmitResponse> getAllStudentByHomeWork(HomeworkSubmitFilterRequest filter, User student) {
        var specification = HomeWorkSubmitSpecification.filterHomeworkSubmit(
                filter.search(),
                filter.homeWorkId(),
                filter.groupId(),
                student.getId(),
                filter.fromScore(),
                filter.toScore(),
                filter.status()

        );

        List<HomeworkSubmit> homeWorks = homeworkSubmitRepository.findAll(specification);
        return homeworkSubmitMapper.toResponse(homeWorks);
    }

    public void checkHomework(HomeworkSubmitCheckRequest request, User authUser) {
        if(authUser.getRole() != User.Role.TEACHER){
            throw new BadRequestException("You are not Teacher");
        }
        HomeworkSubmit homeworkSubmit = homeworkSubmitRepository.findById(request.homeworkSubmitId())
                .orElseThrow(()-> new NotFoundException("Homework submit not found"));
                homeworkSubmit.setScore(request.score());
                homeworkSubmit.setStatus(HomeworkSubmit.Status.CHECKED);
                homeworkSubmitRepository.save(homeworkSubmit);
    }
}

