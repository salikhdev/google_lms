package uz.salikhdev.google_lms.schedule;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.salikhdev.google_lms.domain.entity.academic.Group;
import uz.salikhdev.google_lms.domain.entity.academic.GroupHomework;
import uz.salikhdev.google_lms.domain.entity.academic.Homework;
import uz.salikhdev.google_lms.domain.entity.resource.Resource;
import uz.salikhdev.google_lms.repository.GroupHomeworkRepository;
import uz.salikhdev.google_lms.repository.GroupRepository;
import uz.salikhdev.google_lms.repository.HomeworkRepository;
import uz.salikhdev.google_lms.repository.ResourceRepository;
import uz.salikhdev.google_lms.service.homework.HomeworkService;
import uz.salikhdev.google_lms.service.resource.ResourceService;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GlobalSchedule {
    private final GroupHomeworkRepository groupHomeworkRepository;
    private final ResourceService resourceService;
    private final HomeworkService  homeworkService;
    private final ResourceRepository resourceRepository;
    private final HomeworkRepository homeworkRepository;
    private final GroupRepository groupRepository;


    @Scheduled(fixedDelay = 60_000, initialDelay = 60_000) // every
    public void groupHomeworkSchedular() {

            List<GroupHomework> passedHomeworks = groupHomeworkRepository.findAllByDeadlineBeforeAndIsSubmittedFalse(LocalDateTime.now());
            for (GroupHomework groupHomework : passedHomeworks) {
                groupHomework.setIsSubmitted(true);
                groupHomeworkRepository.save(groupHomework);

        }
    }
    @Scheduled(cron = "0 0 0 * * ?") // har kuni 00:00 da (yani kechqurun 12 da)
    public void resourceDelete() {
        List<Resource> passedResources = resourceRepository.findByStatus(Resource.Status.PASSIVE);
        for (Resource resource : passedResources) {
            resourceService.deleteResource(resource.getKey());
        }
    }
    @Scheduled(fixedDelay = 600_000, initialDelay = 120_000) // 10 minut = 600_000 ms, 2 minut = 120_000 ms
    public void homeworkDelete(){
        LocalDateTime threeHoursAgo = LocalDateTime.now().minusHours(3);
        List<Homework> passedHomeworks = homeworkRepository.findByCreatedAtBeforeAndStatus(threeHoursAgo, Homework.Status.PENDING);
        for (Homework homework : passedHomeworks) {
            homeworkService.deleteHomework(homework.getId());
        }
    }
    @Scheduled(cron = "0 0 0 * * ?") // ha
    public void groupStatusChanger(){
        List<Group> groups = groupRepository.findByStartDateBefore(LocalDateTime.now());
        for (Group group : groups) {
            group.setStatus(Group.Status.ACTIVE);
            groupRepository.save(group);
        }
    }
}






