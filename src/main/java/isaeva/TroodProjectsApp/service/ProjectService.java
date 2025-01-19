package isaeva.TroodProjectsApp.service;

import isaeva.TroodProjectsApp.dto.ProjectRequestDto;
import isaeva.TroodProjectsApp.dto.ProjectResponseDto;
import isaeva.TroodProjectsApp.exception.ProjectNotFoundException;
import isaeva.TroodProjectsApp.mapper.ProjectMapper;
import isaeva.TroodProjectsApp.model.Project;
import isaeva.TroodProjectsApp.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public List<ProjectResponseDto> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(projectMapper::toResponse)
                .toList();
    }

    public ProjectResponseDto getProjectById(Long id) {
        Project project = projectRepository.findById(id).
                orElseThrow(() -> new ProjectNotFoundException("Project with id " + id + " not found"));
        return projectMapper.toResponse(project);
    }

    public ProjectResponseDto createProject(ProjectRequestDto request) {
        Project project = projectMapper.toEntity(request);
        project.setVacancies(List.of());
        Project savedProject = projectRepository.save(project);
        return projectMapper.toResponse(savedProject);
    }

    public ProjectResponseDto updateProject(Long id, ProjectRequestDto request) {

        Project existingProject = projectRepository.findById(id).
                orElseThrow(() -> new ProjectNotFoundException("Project with id " + id + " not found"));
        projectMapper.updateFromRequest(request, existingProject);
        Project updetedProject = projectRepository.save(existingProject);

        return projectMapper.toResponse(updetedProject);


    }

    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id).
                orElseThrow(() -> new ProjectNotFoundException("Project with id " + id + " not found"));
        projectRepository.delete(project);
    }
}
