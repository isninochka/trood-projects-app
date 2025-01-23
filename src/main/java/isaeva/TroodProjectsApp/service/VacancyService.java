package isaeva.TroodProjectsApp.service;

import isaeva.TroodProjectsApp.dto.VacancyDto;
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

    public List<VacancyDto> getVacanciesByProjectId(Long projectId) {
        List<Vacancy> vacancies = vacancyRepository.findByProjectId(projectId);

        return vacancyMapper.toListDto(vacancies);
    }

    public VacancyDto createVacancy(VacancyDto request) {
        Project project = projectRepository.findById(request.projectId()).
                orElseThrow(() -> new ProjectNotFoundException(
                        "Project with id " + request.projectId() + " not found"));

        Vacancy vacancy = vacancyMapper.toEntity(request, project);
        Vacancy savedVacancy = vacancyRepository.save(vacancy);
        return vacancyMapper.toDto(savedVacancy);
    }

    public VacancyDto updateVacancy(Long id, VacancyDto request) {

        Vacancy existingVacancy = vacancyRepository.findById(id).
                orElseThrow(() -> new VacancyNotFoundException("Vacancy with id " + id + " not found"));

        Project project = projectRepository.findById(request.projectId()).
                orElseThrow(() -> new ProjectNotFoundException("Project with id " + request.projectId() + " not found"));

        existingVacancy.setTitle(request.title());
        existingVacancy.setDescription(request.description());
        existingVacancy.setProject(project);

        Vacancy updatedVacancy = vacancyRepository.save(existingVacancy);
        return vacancyMapper.toDto(updatedVacancy);
    }

    public void deleteVacancy(Long id) {
        if (!vacancyRepository.existsById(id)) {
            throw new VacancyNotFoundException("Vacancy with id " + id + " not found");
        }

        vacancyRepository.deleteById(id);
    }


}
