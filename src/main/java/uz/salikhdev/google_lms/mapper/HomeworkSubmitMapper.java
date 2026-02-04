package uz.salikhdev.google_lms.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.salikhdev.google_lms.domain.dto.response.HomeworkSubmitResponse;
import uz.salikhdev.google_lms.domain.entity.academic.HomeworkSubmit;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HomeworkSubmitMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "studentFullName",
            expression = "java(homeworkSubmit.getStudent().getFirstName() + \" \" + homeworkSubmit.getStudent().getLastName())"
    )
    @Mapping(target ="homeWorkId", source = "homeWork.id")
    @Mapping(target = "homeWorkUrl", source = "homeWorkUrl")
    HomeworkSubmitResponse toResponse(HomeworkSubmit homeworkSubmit);

    List<HomeworkSubmitResponse> toResponse(List<HomeworkSubmit> homeworkSubmits);
}
