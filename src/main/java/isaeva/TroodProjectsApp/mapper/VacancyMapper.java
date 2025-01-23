package isaeva.TroodProjectsApp.mapper;

import isaeva.TroodProjectsApp.dto.VacancyDto;
import isaeva.TroodProjectsApp.model.Project;
import isaeva.TroodProjectsApp.model.Vacancy;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = ProjectMapper.class)
public interface VacancyMapper {

    @Mapping(source = "project.id", target = "projectId")
    VacancyDto toDto(Vacancy vacancy);

    VacancyDto toResponse(Vacancy vacancy);

    @Mapping(source = "projectId", target = "project")
    Vacancy toEntity(VacancyDto vacancyDto, @Context Project project);

    List<VacancyDto> toListDto(List<Vacancy> vacancyList);
}
