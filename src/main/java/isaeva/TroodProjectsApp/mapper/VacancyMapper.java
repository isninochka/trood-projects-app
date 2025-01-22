package isaeva.TroodProjectsApp.mapper;

import isaeva.TroodProjectsApp.dto.VacancyRequestDto;
import isaeva.TroodProjectsApp.dto.VacancyResponseDto;
import isaeva.TroodProjectsApp.model.Vacancy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface VacancyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "project", ignore = true)
    Vacancy toEntity(VacancyRequestDto request);

    VacancyResponseDto toResponse(Vacancy vacancy);

    List<VacancyResponseDto> toList(List<Vacancy> vacancies);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "project", ignore = true)
    void updateFromRequest(VacancyRequestDto request, @MappingTarget Vacancy vacancy);
}
