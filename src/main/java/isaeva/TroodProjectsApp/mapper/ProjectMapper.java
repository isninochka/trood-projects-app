package isaeva.TroodProjectsApp.mapper;

import isaeva.TroodProjectsApp.dto.ProjectDto;
import isaeva.TroodProjectsApp.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectDto toDto(Project project);

    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "vacancies")
    Project toEntity(ProjectDto projectDto);

    List<ProjectDto> toListDto(List<Project> projects);
}

