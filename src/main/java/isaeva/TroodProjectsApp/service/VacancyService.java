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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyService {

    private final VacancyRepository vacancyRepository;
    private final ProjectRepository projectRepository;
    private final VacancyMapper vacancyMapper;

    public List<VacancyResponseDto> getVacanciesByProjectId(Long projectId) {
        if (projectRepository.findById(projectId).isEmpty())
            throw new ProjectNotFoundException("Project with id " + projectId + " not found");
        return vacancyRepository.findByProjectId(projectId).stream()
                .map(vacancyMapper::toResponse)
                .toList();
    }

    public VacancyResponseDto createVacancy(Long projectId, VacancyRequestDto request) {
        Project project = projectRepository.findById(projectId).
                orElseThrow(() -> new ProjectNotFoundException("Project with id " + projectId + " not found"));

        Vacancy vacancy = vacancyMapper.toEntity(request);
        vacancy.setProject(project);
        Vacancy savedVacancy = vacancyRepository.save(vacancy);
        return vacancyMapper.toResponse(savedVacancy);
    }

    public VacancyResponseDto updateVacancy(Long id, VacancyRequestDto request) {

        Vacancy existingVacancy = vacancyRepository.findById(id).
                orElseThrow(() -> new VacancyNotFoundException("Vacancy with id " + id + " not found"));

        vacancyMapper.updateFromRequest(request, existingVacancy);
        Vacancy updatedVacancy = vacancyRepository.save(existingVacancy);

        return vacancyMapper.toResponse(updatedVacancy);
    }

    public void deleteVacancy(Long id) {
        Vacancy vacancy = vacancyRepository.findById(id).
                orElseThrow(() -> new VacancyNotFoundException("Vacancy with id " + id + " not found"));
        vacancyRepository.delete(vacancy);
    }


}
