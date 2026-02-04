package uz.salikhdev.google_lms.mapper;


import org.mapstruct.Mapper;
import uz.salikhdev.google_lms.domain.dto.request.HomeworkCreateRequest;
import uz.salikhdev.google_lms.domain.entity.academic.HomeWork;

@Mapper(componentModel = "spring")
public interface HomeworkMapper {
    HomeWork toEntity(HomeworkCreateRequest homeworkCreateRequest);
}
