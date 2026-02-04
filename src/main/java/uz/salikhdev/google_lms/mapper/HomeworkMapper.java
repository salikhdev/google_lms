package uz.salikhdev.google_lms.mapper;


import org.mapstruct.Mapper;
import uz.salikhdev.google_lms.domain.dto.request.HomeworkCreateRequest;
import uz.salikhdev.google_lms.domain.dto.response.HomeworkResponse;
import uz.salikhdev.google_lms.domain.entity.academic.Homework;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HomeworkMapper {

    Homework toEntity(HomeworkCreateRequest homeworkCreateRequest);

    HomeworkResponse toResponse(Homework homework);

    List<HomeworkResponse> toResponse(List<Homework> homework);
}
