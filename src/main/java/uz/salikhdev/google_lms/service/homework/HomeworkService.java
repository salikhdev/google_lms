package uz.salikhdev.google_lms.service.homework;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.salikhdev.google_lms.domain.dto.request.GroupHomeWorkAttachRequest;
import uz.salikhdev.google_lms.domain.dto.request.GroupHomeworkResponse;
import uz.salikhdev.google_lms.domain.dto.request.HomeworkCreateRequest;
import uz.salikhdev.google_lms.domain.dto.request.HomeWorkNotificationRequest;
import uz.salikhdev.google_lms.domain.dto.request.HomeWorkUpdateRequest;
import uz.salikhdev.google_lms.domain.dto.response.HomeworkResponse;
import uz.salikhdev.google_lms.domain.entity.academic.Group;
import uz.salikhdev.google_lms.domain.entity.academic.GroupHomework;
import uz.salikhdev.google_lms.domain.entity.academic.GroupStudent;
import uz.salikhdev.google_lms.domain.entity.academic.Homework;
import uz.salikhdev.google_lms.domain.entity.resource.Resource;
import uz.salikhdev.google_lms.domain.entity.user.User;
import uz.salikhdev.google_lms.exception.ConflictException;
import uz.salikhdev.google_lms.exception.NotFoundException;
import uz.salikhdev.google_lms.mapper.GroupHomeworkMapper;
import uz.salikhdev.google_lms.mapper.HomeworkMapper;
import uz.salikhdev.google_lms.repository.GroupHomeworkRepository;
import uz.salikhdev.google_lms.repository.GroupRepository;
import uz.salikhdev.google_lms.repository.GroupStudentsRepository;
import uz.salikhdev.google_lms.repository.HomeworkRepository;
import uz.salikhdev.google_lms.repository.ResourceRepository;
import uz.salikhdev.google_lms.service.sender.EmailSenderService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeworkService {

    private final HomeworkMapper homeworkMapper;
    private final HomeworkRepository homeworkRepository;
    private final ResourceRepository resourceRepository;
    private final EmailSenderService  emailSenderService;
    private final GroupHomeworkMapper groupHomeworkMapper;
    private final GroupStudentsRepository groupStudentsRepository;
    private final GroupHomeworkRepository groupHomeworkRepository;
    private final GroupRepository groupRepository;


    // ================= Homework ================= //

    public List<HomeworkResponse> getAllHomeworks() {
        List<Homework> homeworks = homeworkRepository.findAll();
        return homeworkMapper.toResponse(homeworks);
    }

    public void createHomework(HomeworkCreateRequest request) {

        if (homeworkRepository.existsByTitle(request.title())) {
            throw new ConflictException("Homework with this title already exists");
        }

        Homework entity = homeworkMapper.toEntity(request);
        //--------------------------------
        entity.setStatus(Homework.Status.PENDING);
        //-----------------------------------
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

    public void attachHomeworkToGroup(User creator, Long groupId, GroupHomeWorkAttachRequest request) {
        if (groupHomeworkRepository.existsByHomeworkId(request.homeworkId())) {
            throw new ConflictException("Homework has already been attached");
        }

        Homework homework = homeworkRepository.findById(request.homeworkId())
                .orElseThrow(() -> new NotFoundException("Homework not found"));


        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group not found"));

        GroupHomework groupHomework = GroupHomework.builder()
                .creator(creator)
                .homework(homework)
                .group(group)
                .deadline(request.deadline())
                .isSubmitted(false)
                .build();

        GroupHomework saveGH = groupHomeworkRepository.save(groupHomework);
        //-------------------------------
        homework.setStatus(Homework.Status.ACTIVE);
        homeworkRepository.save(homework);
        //-------------------------------
        sendNotificationToStudents(saveGH);
    }

    public List<GroupHomeworkResponse> getAllAttachHomeworks(Long groupId) {
        List<GroupHomework> gh = groupHomeworkRepository.findByGroup_Id(groupId);
        return groupHomeworkMapper.toResponse(gh);
    }


    // ================= Send notification students ================= //

    private void sendNotificationToStudents(GroupHomework groupHomework) {
        List<GroupStudent> groupStudents = groupStudentsRepository.findAllByGroup_Id(groupHomework.getGroup().getId());
        for (GroupStudent groupStudent : groupStudents) {
            HomeWorkNotificationRequest homeWorkNotificationRequest = HomeWorkNotificationRequest.builder()
                    .teacherName(groupHomework.getGroup().getMentor().getFirstName())
                    .homeWorkTitle(groupHomework.getHomework().getTitle())
                    .groupName(groupHomework.getGroup().getName())
                    .deadline(groupHomework.getDeadline())
                    .firstName(groupStudent.getStudent().getFirstName())
                    .lastName(groupStudent.getStudent().getLastName())
                    .build();
            emailSenderService.sendNotificationForHomework(groupStudent.getStudent().getEmail(), homeWorkNotificationRequest);
        }
    }

    public void update(Long homeWorkId, HomeWorkUpdateRequest request) {
        Homework homeWork = homeworkRepository.findById(homeWorkId)
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