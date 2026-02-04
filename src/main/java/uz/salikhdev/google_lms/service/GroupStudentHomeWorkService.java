package uz.salikhdev.google_lms.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.salikhdev.google_lms.domain.dto.request.GroupStudentHomeWorkCreateRequest;
import uz.salikhdev.google_lms.domain.entity.academic.Group;
import uz.salikhdev.google_lms.domain.entity.academic.GroupStudentHomeWork;
import uz.salikhdev.google_lms.domain.entity.academic.HomeWork;
import uz.salikhdev.google_lms.domain.entity.resource.Resource;
import uz.salikhdev.google_lms.domain.entity.user.User;
import uz.salikhdev.google_lms.exception.NotFoundException;
import uz.salikhdev.google_lms.repository.*;

@Service
@RequiredArgsConstructor
public class GroupStudentHomeWorkService {
    private final GroupStudentHomeWorkRepository groupStudentHomeWorkRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final HomeworkRepository homeworkRepository;
    private final ResourceRepository resourceRepository;


    public void submitHomework(GroupStudentHomeWorkCreateRequest request, User student) {
        Group group= groupRepository.findById(request.groupId())
                .orElseThrow(()-> new NotFoundException("Group not found"));
        HomeWork homeWork= homeworkRepository.findById(request.homeWorkId())
                .orElseThrow(()-> new NotFoundException("Home work not found"));
        Resource resource =resourceRepository.findByUrlAndStatusNot(request.homeWorkUrl(), Resource.Status.DELETED)
                .orElseThrow(() -> new NotFoundException("Resource not found"));

        GroupStudentHomeWork  groupStudentHomeWork = GroupStudentHomeWork.builder()
                .group(group)
                .student(student)
                .homework(homeWork)
                .student(student)
                .description(request.description())
                .status(GroupStudentHomeWork.Status.PENDING)
                .build();

        groupStudentHomeWorkRepository.save(groupStudentHomeWork);

    }
}
