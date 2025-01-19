package isaeva.TroodProjectsApp.mapper;

import isaeva.TroodProjectsApp.dto.ProjectRequestDto;
import isaeva.TroodProjectsApp.dto.ProjectResponseDto;
import isaeva.TroodProjectsApp.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring", uses = VacancyMapper.class)
public interface ProjectMapper {

    Project toEntity(ProjectRequestDto request);

    ProjectResponseDto toResponse(Project project);

    void updateFromRequest(ProjectRequestDto request, @MappingTarget Project existingProject);
}

