package uz.salikhdev.google_lms.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.salikhdev.google_lms.domain.dto.request.UserResponse;
import uz.salikhdev.google_lms.domain.dto.response.GroupStudentHomeWorkResponse;
import uz.salikhdev.google_lms.domain.entity.academic.GroupStudentHomeWork;
import uz.salikhdev.google_lms.domain.entity.user.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupStudentHomeWorkMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "studentFullName",
            expression = "java(groupStudentHomeWork.getStudent().getFirstName() + \" \" + groupStudentHomeWork.getStudent().getLastName())"
    )
    @Mapping(target ="homeWorkId", source = "homeWork.id")
    @Mapping(target = "homeWorkUrl", source = "homeWorkUrl")
    GroupStudentHomeWorkResponse toResponse(GroupStudentHomeWork groupStudentHomeWork);

    List<GroupStudentHomeWorkResponse> toResponse(List<GroupStudentHomeWork> groupStudentHomeWorks);
}
