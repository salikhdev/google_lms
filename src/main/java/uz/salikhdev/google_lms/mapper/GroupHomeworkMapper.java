package uz.salikhdev.google_lms.mapper;


import org.mapstruct.Mapper;
import uz.salikhdev.google_lms.domain.dto.request.GroupHomeworkResponse;
import uz.salikhdev.google_lms.domain.entity.academic.GroupHomework;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupHomeworkMapper {

    GroupHomeworkResponse toResponse(GroupHomework groupHomework);

    List<GroupHomeworkResponse> toResponse(List<GroupHomework> groupHomeworks);
}
