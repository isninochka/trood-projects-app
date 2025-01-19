package isaeva.TroodProjectsApp.service;

import isaeva.TroodProjectsApp.dto.ProjectRequestDto;
import isaeva.TroodProjectsApp.dto.ProjectResponseDto;
import isaeva.TroodProjectsApp.exception.ProjectNotFoundException;
import isaeva.TroodProjectsApp.mapper.ProjectMapper;
import isaeva.TroodProjectsApp.model.Project;
import isaeva.TroodProjectsApp.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@AutoConfigureMockMvc
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMapper projectMapper;

    @InjectMocks
    private ProjectService projectService;

    private Project project;
    private ProjectRequestDto projectRequestDto;
    private ProjectResponseDto projectResponseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        project = new Project(1L, "Test Project", "Test Description", new ArrayList<>());
        projectRequestDto = new ProjectRequestDto("Test Project", "Test Description");
        projectResponseDto = new ProjectResponseDto(1L, "Test Project", "Test Description",
                new ArrayList<>());

    }

    @Test
    void testGetAllProjects() {

        List<Project> projects = List.of(project);
        when(projectRepository.findAll()).thenReturn(projects);
        when(projectMapper.toResponse(project)).thenReturn(projectResponseDto);

        List<ProjectResponseDto> result = projectService.getAllProjects();

        assertEquals(1, result.size());
        assertEquals("Test Project", result.get(0).name());
        verify(projectRepository, times(1)).findAll();
        verify(projectMapper, times(1)).toResponse(project);
    }

    @Test
    void testGetProjectById_Success() {

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectMapper.toResponse(project)).thenReturn(projectResponseDto);

        ProjectResponseDto result = projectService.getProjectById(1L);

        assertNotNull(result);
        assertEquals("Test Project", result.name());
        verify(projectRepository, times(1)).findById(1L);
        verify(projectMapper, times(1)).toResponse(project);
    }

    @Test
    void testGetProjectById_NotFound() {

        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        ProjectNotFoundException exception = assertThrows(ProjectNotFoundException.class, () -> {
            projectService.getProjectById(1L);
        });
        assertEquals("Project with id 1 not found", exception.getMessage());
    }

    @Test
    void testCreateProject() {

        when(projectMapper.toEntity(projectRequestDto)).thenReturn(project);
        when(projectRepository.save(project)).thenReturn(project);
        when(projectMapper.toResponse(project)).thenReturn(projectResponseDto);

        ProjectResponseDto result = projectService.createProject(projectRequestDto);

        assertNotNull(result);
        assertEquals("Test Project", result.name());
        verify(projectMapper, times(1)).toEntity(projectRequestDto);
        verify(projectRepository, times(1)).save(project);
        verify(projectMapper, times(1)).toResponse(project);
    }

    @Test
    void testUpdateProject_Success() {

        ProjectRequestDto updateRequest = new ProjectRequestDto("Updated Project", "Updated Description");
        Project existingProject = new Project(1L, "Updated Project", "Updated Description", new ArrayList<>());
        Project updatedProject = new Project(1L, "Updated Project", "Updated Description", new ArrayList<>());
        ProjectResponseDto updatedResponse = new ProjectResponseDto(1L, "Updated Project", "Updated Description", new ArrayList<>());

        when(projectRepository.findById(1L)).thenReturn(Optional.of(existingProject));
        when(projectMapper.toResponse(updatedProject)).thenReturn(updatedResponse);
        when(projectRepository.save(existingProject)).thenReturn(updatedProject);

        ProjectResponseDto result = projectService.updateProject(1L, updateRequest);

        assertNotNull(result);
        assertEquals("Updated Project", result.name());
        assertEquals("Updated Description", result.description());

        verify(projectRepository, times(1)).findById(1L);
        verify(projectMapper, times(1)).updateFromRequest(updateRequest, existingProject);
        verify(projectRepository, times(1)).save(existingProject);
        verify(projectMapper, times(1)).toResponse(updatedProject);


    }

    @Test
    void testUpdateProject_NotFound() {

        ProjectRequestDto updateRequest = new ProjectRequestDto("Updated Project", "Updated Description");
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        ProjectNotFoundException exception = assertThrows(ProjectNotFoundException.class, () -> {
            projectService.updateProject(1L, updateRequest);
        });
        assertEquals("Project with id 1 not found", exception.getMessage());
    }

    @Test
    void testDeleteProject_Success() {

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        projectService.deleteProject(1L);

        verify(projectRepository, times(1)).delete(project);
    }

    @Test
    void testDeleteProject_NotFound() {

        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        ProjectNotFoundException exception = assertThrows(ProjectNotFoundException.class, () -> {
            projectService.deleteProject(1L);
        });
        assertEquals("Project with id 1 not found", exception.getMessage());
    }
}