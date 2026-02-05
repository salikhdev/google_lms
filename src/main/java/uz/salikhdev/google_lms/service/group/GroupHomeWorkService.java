package uz.salikhdev.google_lms.service.group;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.salikhdev.google_lms.domain.dto.request.GroupHomeworkFilterRequest;
import uz.salikhdev.google_lms.domain.dto.request.GroupHomeworkResponse;
import uz.salikhdev.google_lms.domain.entity.academic.GroupHomework;
import uz.salikhdev.google_lms.domain.entity.academic.HomeworkSubmit;
import uz.salikhdev.google_lms.domain.entity.user.User;
import uz.salikhdev.google_lms.mapper.GroupHomeworkMapper;
import uz.salikhdev.google_lms.repository.GroupHomeworkRepository;
import uz.salikhdev.google_lms.specification.GroupHomeworkSpecification;
import uz.salikhdev.google_lms.specification.HomeWorkSubmitSpecification;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupHomeWorkService {
    private final GroupHomeworkRepository groupHomeworkRepository;
    private final GroupHomeworkMapper groupHomeworkMapper;


    public List<GroupHomeworkResponse> getAllForTeachers(GroupHomeworkFilterRequest filter) {
        var specification = GroupHomeworkSpecification.filterGroupHomeworks(
                filter.homeworkId(),
                filter.groupId(),
                filter.creatorId(),
                filter.isSubmitted(),
                filter.fromDate(),
                filter.toDate()

        );
        List<GroupHomework> groupHomeWorks = groupHomeworkRepository.findAll(specification);
        return groupHomeworkMapper.toResponse(groupHomeWorks);

    }

}
