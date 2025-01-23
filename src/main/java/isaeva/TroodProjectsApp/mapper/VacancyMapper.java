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

    @Mapping(ignore = true, target = "projectId")
    VacancyDto toDto(Vacancy vacancy);

    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "project")
    Vacancy toEntity(VacancyDto vacancyDto, @Context Project project);

    List<VacancyDto> toListDto(List<Vacancy> vacancyList);
}
