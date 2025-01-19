package isaeva.TroodProjectsApp.mapper;

import isaeva.TroodProjectsApp.dto.VacancyRequestDto;
import isaeva.TroodProjectsApp.dto.VacancyResponseDto;
import isaeva.TroodProjectsApp.model.Vacancy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VacancyMapper {

    Vacancy toEntity(VacancyRequestDto request);

    VacancyResponseDto toResponse(Vacancy vacancy);

    List<VacancyResponseDto> toList(List<Vacancy> vacancies);

    void updateFromRequest(VacancyRequestDto request, @MappingTarget Vacancy vacancy);
}
