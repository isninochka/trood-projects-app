package isaeva.TroodProjectsApp.service;

import isaeva.TroodProjectsApp.dto.VacancyDto;
import isaeva.TroodProjectsApp.exception.ProjectNotFoundException;
import isaeva.TroodProjectsApp.exception.VacancyNotFoundException;
import isaeva.TroodProjectsApp.mapper.VacancyMapper;
import isaeva.TroodProjectsApp.model.Project;
import isaeva.TroodProjectsApp.model.Vacancy;
import isaeva.TroodProjectsApp.repository.ProjectRepository;
import isaeva.TroodProjectsApp.repository.VacancyRepository;
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
class VacancyServiceTest {

    @Mock
    private VacancyRepository vacancyRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private VacancyMapper vacancyMapper;

    @InjectMocks
    private VacancyService vacancyService;

    @Test
    void getVacanciesByProjectId_shouldReturnListOfVacancies() {
        // Arrange
        Long projectId = 1L;
        List<Vacancy> vacancies = List.of(
                new Vacancy(1L, "Vacancy 1", "Description 1", new Project(projectId, "Project 1", "Description 1")),
                new Vacancy(2L, "Vacancy 2", "Description 2", new Project(projectId, "Project 1", "Description 1"))
        );
        List<VacancyDto> vacancyDtos = List.of(
                new VacancyDto("Vacancy 1", "Description 1", projectId),
                new VacancyDto("Vacancy 2", "Description 2", projectId)
        );

        Mockito.when(vacancyRepository.findByProjectId(projectId)).thenReturn(vacancies);
        Mockito.when(vacancyMapper.toListDto(vacancies)).thenReturn(vacancyDtos);

        // Act
        List<VacancyDto> result = vacancyService.getVacanciesByProjectId(projectId);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Vacancy 1", result.getFirst().title());
        assertEquals("Description 1", result.getFirst().description());

        Mockito.verify(vacancyRepository).findByProjectId(projectId);
        Mockito.verify(vacancyMapper).toListDto(vacancies);
    }

    @Test
    void createVacancy_shouldSaveAndReturnVacancyDto() {
        // Arrange
        Long projectId = 1L;
        Project project = new Project(projectId, "Project 1", "Description 1");
        Vacancy vacancy = new Vacancy(null, "New Vacancy", "New Description", project);
        Vacancy savedVacancy = new Vacancy(1L, "New Vacancy", "New Description", project);
        VacancyDto request = new VacancyDto("New Vacancy", "New Description", projectId);
        VacancyDto response = new VacancyDto("New Vacancy", "New Description", projectId);

        Mockito.when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        Mockito.when(vacancyMapper.toEntity(request, project)).thenReturn(vacancy);
        Mockito.when(vacancyRepository.save(vacancy)).thenReturn(savedVacancy);
        Mockito.when(vacancyMapper.toDto(savedVacancy)).thenReturn(response);

        // Act
        VacancyDto result = vacancyService.createVacancy(request);

        // Assert
        assertEquals("New Vacancy", result.title());
        assertEquals("New Description", result.description());
        assertEquals(projectId, result.projectId());

        Mockito.verify(projectRepository).findById(projectId);
        Mockito.verify(vacancyMapper).toEntity(request, project);
        Mockito.verify(vacancyRepository).save(vacancy);
        Mockito.verify(vacancyMapper).toDto(savedVacancy);
    }

    @Test
    void createVacancy_shouldThrowExceptionIfProjectNotFound() {
        // Arrange
        Long projectId = 1L;
        VacancyDto request = new VacancyDto("New Vacancy", "New Description", projectId);

        Mockito.when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProjectNotFoundException.class, () -> vacancyService.createVacancy(request));

        Mockito.verify(projectRepository).findById(projectId);
        Mockito.verifyNoInteractions(vacancyRepository, vacancyMapper);
    }

    @Test
    void updateVacancy_shouldUpdateAndReturnVacancyDto() {
        // Arrange
        Long vacancyId = 1L;
        Long projectId = 1L;
        Project project = new Project(projectId, "Project 1", "Description 1");
        Vacancy existingVacancy = new Vacancy(vacancyId, "Old Vacancy", "Old Description", project);
        Vacancy updatedVacancy = new Vacancy(vacancyId, "Updated Vacancy", "Updated Description", project);
        VacancyDto request = new VacancyDto("Updated Vacancy", "Updated Description", projectId);
        VacancyDto response = new VacancyDto("Updated Vacancy", "Updated Description", projectId);

        Mockito.when(vacancyRepository.findById(vacancyId)).thenReturn(Optional.of(existingVacancy));
        Mockito.when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        Mockito.when(vacancyRepository.save(existingVacancy)).thenReturn(updatedVacancy);
        Mockito.when(vacancyMapper.toDto(updatedVacancy)).thenReturn(response);

        // Act
        VacancyDto result = vacancyService.updateVacancy(vacancyId, request);

        // Assert
        assertEquals("Updated Vacancy", result.title());
        assertEquals("Updated Description", result.description());
        assertEquals(projectId, result.projectId());

        Mockito.verify(vacancyRepository).findById(vacancyId);
        Mockito.verify(projectRepository).findById(projectId);
        Mockito.verify(vacancyRepository).save(existingVacancy);
        Mockito.verify(vacancyMapper).toDto(updatedVacancy);
    }

    @Test
    void updateVacancy_shouldThrowExceptionIfVacancyNotFound() {
        // Arrange
        Long vacancyId = 1L;
        VacancyDto request = new VacancyDto("Updated Vacancy", "Updated Description", 1L);

        Mockito.when(vacancyRepository.findById(vacancyId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(VacancyNotFoundException.class, () -> vacancyService.updateVacancy(vacancyId, request));

        Mockito.verify(vacancyRepository).findById(vacancyId);
        Mockito.verifyNoInteractions(projectRepository, vacancyMapper);
    }

    @Test
    void updateVacancy_shouldThrowExceptionIfProjectNotFound() {
        // Arrange
        Long vacancyId = 1L;
        Long projectId = 1L;
        Vacancy existingVacancy = new Vacancy(vacancyId, "Old Vacancy", "Old Description", null);
        VacancyDto request = new VacancyDto("Updated Vacancy", "Updated Description", projectId);

        Mockito.when(vacancyRepository.findById(vacancyId)).thenReturn(Optional.of(existingVacancy));
        Mockito.when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProjectNotFoundException.class, () -> vacancyService.updateVacancy(vacancyId, request));

        Mockito.verify(vacancyRepository).findById(vacancyId);
        Mockito.verify(projectRepository).findById(projectId);
        Mockito.verifyNoInteractions(vacancyMapper);
    }

    @Test
    void deleteVacancy_shouldDeleteWhenExists() {
        // Arrange
        Long vacancyId = 1L;

        Mockito.when(vacancyRepository.existsById(vacancyId)).thenReturn(true);

        // Act
        vacancyService.deleteVacancy(vacancyId);

        // Assert
        Mockito.verify(vacancyRepository).existsById(vacancyId);
        Mockito.verify(vacancyRepository).deleteById(vacancyId);
    }

    @Test
    void deleteVacancy_shouldThrowExceptionIfNotFound() {
        // Arrange
        Long vacancyId = 1L;

        Mockito.when(vacancyRepository.existsById(vacancyId)).thenReturn(false);

        // Act & Assert
        assertThrows(VacancyNotFoundException.class, () -> vacancyService.deleteVacancy(vacancyId));

        Mockito.verify(vacancyRepository).existsById(vacancyId);
        Mockito.verifyNoMoreInteractions(vacancyRepository);
    }
}
