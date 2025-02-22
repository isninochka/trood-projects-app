package isaeva.TroodProjectsApp.service;

import isaeva.TroodProjectsApp.dto.ProjectDto;
import isaeva.TroodProjectsApp.exception.ProjectNotFoundException;
import isaeva.TroodProjectsApp.mapper.ProjectMapper;
import isaeva.TroodProjectsApp.model.Project;
import isaeva.TroodProjectsApp.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMapper projectMapper;

    @InjectMocks
    private ProjectService projectService;

    @Test
    void getAllProjects_shouldReturnListOfProjects() {

        List<Project> projects = List.of(
                new Project(1L, "Project 1", "Description 1"),
                new Project(2L, "Project 2", "Description 2")
        );
        List<ProjectDto> projectDtos = List.of(
                new ProjectDto("Project 1", "Description 1"),
                new ProjectDto("Project 2", "Description 2")
        );

        Mockito.when(projectRepository.findAll()).thenReturn(projects);
        Mockito.when(projectMapper.toListDto(projects)).thenReturn(projectDtos);

        List<ProjectDto> result = projectService.getAllProjects();

        assertEquals(2, result.size());
        assertEquals("Project 1", result.getFirst().name());
        assertEquals("Description 1", result.getFirst().description());

        Mockito.verify(projectRepository).findAll();
        Mockito.verify(projectMapper).toListDto(projects);
    }

    @Test
    void getProjectById_shouldReturnProjectDto() {

        Project project = new Project(1L, "Project 1", "Description 1");
        ProjectDto projectDto = new ProjectDto("Project 1", "Description 1");

        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        Mockito.when(projectMapper.toDto(project)).thenReturn(projectDto);

        ProjectDto result = projectService.getProjectById(1L);

        assertEquals("Project 1", result.name());
        assertEquals("Description 1", result.description());

        Mockito.verify(projectRepository).findById(1L);
        Mockito.verify(projectMapper).toDto(project);
    }

    @Test
    void getProjectById_shouldThrowExceptionWhenNotFound() {

        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProjectNotFoundException.class, () -> projectService.getProjectById(1L));

        Mockito.verify(projectRepository).findById(1L);
    }

    @Test
    void createProject_shouldSaveAndReturnProjectDto() {

        ProjectDto request = new ProjectDto("New Project", "New Description");
        Project project = new Project(null, "New Project", "New Description");
        Project savedProject = new Project(1L, "New Project", "New Description");
        ProjectDto response = new ProjectDto("New Project", "New Description");

        Mockito.when(projectMapper.toEntity(request)).thenReturn(project);
        Mockito.when(projectRepository.save(project)).thenReturn(savedProject);
        Mockito.when(projectMapper.toDto(savedProject)).thenReturn(response);

        ProjectDto result = projectService.createProject(request);

        assertEquals("New Project", result.name());
        assertEquals("New Description", result.description());

        Mockito.verify(projectMapper).toEntity(request);
        Mockito.verify(projectRepository).save(project);
        Mockito.verify(projectMapper).toDto(savedProject);
    }

    @Test
    void updateProject_shouldUpdateAndReturnProjectDto() {

        ProjectDto request = new ProjectDto("Updated Project", "Updated Description");
        Project existingProject = new Project(1L, "Old Project", "Old Description");
        Project updatedProject = new Project(1L, "Updated Project", "Updated Description");
        ProjectDto response = new ProjectDto("Updated Project", "Updated Description");

        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.of(existingProject));
        Mockito.when(projectRepository.save(existingProject)).thenReturn(updatedProject);
        Mockito.when(projectMapper.toDto(updatedProject)).thenReturn(response);

        ProjectDto result = projectService.updateProject(1L, request);

        assertEquals("Updated Project", result.name());
        assertEquals("Updated Description", result.description());

        Mockito.verify(projectRepository).findById(1L);
        Mockito.verify(projectRepository).save(existingProject);
        Mockito.verify(projectMapper).toDto(updatedProject);
    }

    @Test
    void deleteProject_shouldDeleteWhenExists() {

        Mockito.when(projectRepository.existsById(1L)).thenReturn(true);

        projectService.deleteProject(1L);

        Mockito.verify(projectRepository).existsById(1L);
        Mockito.verify(projectRepository).deleteById(1L);
    }

    @Test
    void deleteProject_shouldThrowExceptionWhenNotFound() {

        Mockito.when(projectRepository.existsById(1L)).thenReturn(false);

        assertThrows(ProjectNotFoundException.class, () -> projectService.deleteProject(1L));

        Mockito.verify(projectRepository).existsById(1L);
    }
}