package isaeva.TroodProjectsApp.service;

import isaeva.TroodProjectsApp.dto.ProjectDto;
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

    public List<ProjectDto> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projectMapper.toListDto(projects);
    }

    public ProjectDto getProjectById(Long id) {
        Project project = projectRepository.findById(id).
                orElseThrow(() -> new ProjectNotFoundException("Project with id " + id + " not found"));
        return projectMapper.toDto(project);
    }

    public ProjectDto createProject(ProjectDto request) {
        Project project = projectMapper.toEntity(request);
        return projectMapper.toDto(projectRepository.save(project));
    }

    public ProjectDto updateProject(Long id, ProjectDto request) {

        Project existingProject = projectRepository.findById(id).
                orElseThrow(() -> new ProjectNotFoundException("Project with id " + id + " not found"));
        existingProject.setName(request.name());
        existingProject.setDescription(request.description());
        Project updetedProject = projectRepository.save(existingProject);

        return projectMapper.toDto(updetedProject);
    }

    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new ProjectNotFoundException("Project with id " + id + " not found");
        }
        projectRepository.deleteById(id);
    }
}
