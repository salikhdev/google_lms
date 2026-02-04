package uz.salikhdev.google_lms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.salikhdev.google_lms.domain.dto.request.GroupHomeWorkAttachRequest;
import uz.salikhdev.google_lms.domain.dto.request.HomeworkCreateRequest;
import uz.salikhdev.google_lms.domain.dto.request.SendNotificationForHomeWorkRequest;
import uz.salikhdev.google_lms.domain.dto.request.UpdateHomeWorkRequest;
import uz.salikhdev.google_lms.domain.entity.academic.Group;
import uz.salikhdev.google_lms.domain.entity.academic.GroupHomework;
import uz.salikhdev.google_lms.domain.entity.academic.GroupStudent;
import uz.salikhdev.google_lms.domain.entity.academic.HomeWork;
import uz.salikhdev.google_lms.domain.entity.resource.Resource;
import uz.salikhdev.google_lms.domain.entity.user.User;
import uz.salikhdev.google_lms.exception.ConflictException;
import uz.salikhdev.google_lms.exception.NotFoundException;
import uz.salikhdev.google_lms.mapper.HomeworkMapper;
import uz.salikhdev.google_lms.repository.*;
import uz.salikhdev.google_lms.service.sender.EmailSenderService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeworkService {

    private final HomeworkMapper homeworkMapper;
    private final HomeworkRepository homeworkRepository;
    private final ResourceRepository resourceRepository;
    private final EmailSenderService  emailSenderService;
    private final GroupStudentsRepository groupStudentsRepository;
    private final GroupHomeworkRepository groupHomeworkRepository;
    private final GroupRepository groupRepository;


    // ================= Homework ================= //

    public void createHomework(HomeworkCreateRequest request) {

        if (homeworkRepository.existsByTitle(request.title())) {
            throw new ConflictException("Homework with this title already exists");
        }

        HomeWork entity = homeworkMapper.toEntity(request);
        homeworkRepository.save(entity);
    }

    public void deleteHomework(Long homeworkId) {
        if (!homeworkRepository.existsById(homeworkId)) {
            throw new ConflictException("Homework not found");
        }

        if (groupHomeworkRepository.existsByHomeworkId(homeworkId)) {
            throw new ConflictException("Cannot delete homework assigned to groups");
        }

        homeworkRepository.deleteById(homeworkId);
    }


    // ================= Group Homework ================= //

    public void attachHomeworkToGroup(User creator, GroupHomeWorkAttachRequest  request) {
        if (groupHomeworkRepository.existsByHomeworkId(request.homeworkId())) {
            throw new ConflictException("Homework has already been attached");
        }

        HomeWork homework = homeworkRepository.findById(request.homeworkId())
                .orElseThrow(() -> new NotFoundException("Homework not found"));

        Group group = groupRepository.findById(request.groupId())
                .orElseThrow(() -> new NotFoundException("Group not found"));

        GroupHomework groupHomework = GroupHomework.builder()
                .creator(creator)
                .homework(homework)
                .group(group)
                .deadline(request.deadline())
                .isSubmitted(false)
                .build();

        groupHomeworkRepository.save(groupHomework);
    }


    // ================= Send notification students ================= //

    public void sendHomeWorkToGroup(User creator, Long groupHomeWorkId) {
        GroupHomework groupHomework = groupHomeworkRepository.findByIdAndIsSubmittedFalse(groupHomeWorkId)
                .orElseThrow(()-> new NotFoundException("Group homework not found"));
        groupHomework.setIsSubmitted(true);
        groupHomeworkRepository.save(groupHomework);

      List<GroupStudent> groupStudents=groupStudentsRepository.findByGroup_Id(groupHomework.getGroup().getId());
        for (GroupStudent groupStudent1 : groupStudents ) {
            SendNotificationForHomeWorkRequest sendNotificationForHomeWorkRequest = SendNotificationForHomeWorkRequest.builder()
                    .teacherName(creator.getFirstName())
                    .homeWorkTitle(groupHomework.getHomework().getTitle())
                    .groupName(groupHomework.getGroup().getName())
                    .deadline(groupHomework.getDeadline())
                    .firstName(groupStudent1.getStudent().getFirstName())
                    .lastName(groupStudent1.getStudent().getLastName())
                    .build();
            emailSenderService.sendNotificationForHomework(groupStudent1.getStudent().getEmail(), sendNotificationForHomeWorkRequest);
        }


    }

    public void update(Long homeWorkId, UpdateHomeWorkRequest request) {
        HomeWork homeWork= homeworkRepository.findById(homeWorkId)
                .orElseThrow(() -> new NotFoundException("Homework not found"));
        Resource resource =resourceRepository.findByUrlAndStatusNot(request.contentUrl(), Resource.Status.DELETED)
                .orElseThrow(() -> new NotFoundException("Resource not found"));

        if (request.maxScore() != null) {
            homeWork.setMaxScore(request.maxScore());
        }

        if (request.title() != null) {
            homeWork.setTitle(request.title());
        }

        if (request.description() != null) {
            homeWork.setDescription(request.description());
        }

        if (request.contentUrl() != null) {
            homeWork.setContentUrl(request.contentUrl());
        }
        homeworkRepository.save(homeWork);

    }
}
