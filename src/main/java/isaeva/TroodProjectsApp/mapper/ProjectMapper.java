package isaeva.TroodProjectsApp.mapper;

import isaeva.TroodProjectsApp.dto.ProjectDto;
import isaeva.TroodProjectsApp.model.Project;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectDto toDto(Project project);

    Project toEntity(ProjectDto projectDto);

    List<ProjectDto> toDto(List<Project> projects);
}

