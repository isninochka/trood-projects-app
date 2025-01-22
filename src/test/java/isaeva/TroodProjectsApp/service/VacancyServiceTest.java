package isaeva.TroodProjectsApp.service;

import isaeva.TroodProjectsApp.dto.VacancyRequestDto;
import isaeva.TroodProjectsApp.dto.VacancyResponseDto;
import isaeva.TroodProjectsApp.exception.ProjectNotFoundException;
import isaeva.TroodProjectsApp.exception.VacancyNotFoundException;
import isaeva.TroodProjectsApp.mapper.VacancyMapper;
import isaeva.TroodProjectsApp.model.Project;
import isaeva.TroodProjectsApp.model.Vacancy;
import isaeva.TroodProjectsApp.repository.ProjectRepository;
import isaeva.TroodProjectsApp.repository.VacancyRepository;
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
class VacancyServiceTest {


    @Mock
    private VacancyRepository vacancyRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private VacancyMapper vacancyMapper;

    @InjectMocks
    private VacancyService vacancyService;

    private Project project;
    private Vacancy vacancy;
    private VacancyRequestDto vacancyRequestDto;
    private VacancyResponseDto vacancyResponseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        project = new Project(1L, "Test Project", "Test Description", new ArrayList<>());
        vacancy = new Vacancy(1L, "Test Vacancy", "Description", project);
        vacancyRequestDto = new VacancyRequestDto("Test Vacancy", "Description");
        vacancyResponseDto = new VacancyResponseDto(1L, "Test Vacancy", "Description");
    }

    @Test
    void testGetVacanciesByProjectId_Success() {

        when(projectRepository.findById(1L)).thenReturn(Optional.ofNullable(project));
        when(vacancyRepository.findByProjectId(1L)).thenReturn(List.of(vacancy));
        when(vacancyMapper.toResponse(vacancy)).thenReturn(vacancyResponseDto);

        List<VacancyResponseDto> result = vacancyService.getVacanciesByProjectId(1L);

        assertEquals(1, result.size());
        assertEquals("Test Vacancy", result.getFirst().title());
        verify(vacancyRepository, times(1)).findByProjectId(1L);
        verify(vacancyMapper, times(1)).toResponse(vacancy);
    }

    @Test
    void testGetVacanciesByProjectId_ProjectNotFound() {

        when(projectRepository.existsById(1L)).thenReturn(false);

        ProjectNotFoundException exception = assertThrows(ProjectNotFoundException.class, () ->
                vacancyService.getVacanciesByProjectId(1L));
        assertEquals("Project with id 1 not found", exception.getMessage());
    }

    @Test
    void testCreateVacancy_Success() {

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(vacancyMapper.toEntity(vacancyRequestDto)).thenReturn(vacancy);
        when(vacancyRepository.save(vacancy)).thenReturn(vacancy);
        when(vacancyMapper.toResponse(vacancy)).thenReturn(vacancyResponseDto);

        VacancyResponseDto result = vacancyService.createVacancy(1L, vacancyRequestDto);

        assertNotNull(result);
        assertEquals("Test Vacancy", result.title());
        verify(vacancyRepository, times(1)).save(vacancy);
        verify(vacancyMapper, times(1)).toResponse(vacancy);
    }

    @Test
    void testCreateVacancy_ProjectNotFound() {

        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        ProjectNotFoundException exception = assertThrows(ProjectNotFoundException.class, () ->
                vacancyService.createVacancy(1L, vacancyRequestDto));
        assertEquals("Project with id 1 not found", exception.getMessage());
    }

    @Test
    void testUpdateVacancy_Success() {

        VacancyRequestDto updateRequest = new VacancyRequestDto("Updated Vacancy", "Updated Description");
        Vacancy vacancy = new Vacancy(1L, "Old Vacancy", "Old Description", project);
        Vacancy updatedVacancy = new Vacancy(1L, "Updated Vacancy", "Updated Description", project);
        VacancyResponseDto vacancyResponseDto = new VacancyResponseDto(1L, "Updated Vacancy", "Updated Description");

        when(vacancyRepository.findById(1L)).thenReturn(Optional.of(vacancy));
        when(vacancyMapper.toResponse(updatedVacancy)).thenReturn(vacancyResponseDto);
        when(vacancyRepository.save(vacancy)).thenReturn(updatedVacancy);

        VacancyResponseDto result = vacancyService.updateVacancy(1L, updateRequest);

        assertNotNull(result);
        assertEquals("Updated Vacancy", result.title());
        assertEquals("Updated Description", result.description());

        verify(vacancyRepository, times(1)).findById(1L);
        verify(vacancyMapper, times(1)).updateFromRequest(updateRequest, vacancy);
        verify(vacancyRepository, times(1)).save(vacancy);
        verify(vacancyMapper, times(1)).toResponse(updatedVacancy);
    }

    @Test
    void testUpdateVacancy_NotFound() {

        VacancyRequestDto updateRequest = new VacancyRequestDto("Updated Vacancy", "Updated Description");
        when(vacancyRepository.findById(1L)).thenReturn(Optional.empty());

        VacancyNotFoundException exception = assertThrows(VacancyNotFoundException.class, () ->
                vacancyService.updateVacancy(1L, updateRequest));
        assertEquals("Vacancy with id 1 not found", exception.getMessage());
    }

    @Test
    void testDeleteVacancy_Success() {

        when(vacancyRepository.findById(1L)).thenReturn(Optional.of(vacancy));

        vacancyService.deleteVacancy(1L);

        verify(vacancyRepository, times(1)).delete(vacancy);
    }

    @Test
    void testDeleteVacancy_NotFound() {

        when(vacancyRepository.findById(1L)).thenReturn(Optional.empty());

        VacancyNotFoundException exception = assertThrows(VacancyNotFoundException.class, () ->
                vacancyService.deleteVacancy(1L));
        assertEquals("Vacancy with id 1 not found", exception.getMessage());
    }
}