package isaeva.TroodProjectsApp.controller;

import isaeva.TroodProjectsApp.dto.VacancyRequestDto;
import isaeva.TroodProjectsApp.dto.VacancyResponseDto;
import isaeva.TroodProjectsApp.exception.ProjectNotFoundException;
import isaeva.TroodProjectsApp.service.VacancyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/vacancies")
@RequiredArgsConstructor
public class VacancyController {

    private final VacancyService vacancyService;

    /**
     * Retrieves a list of vacancies for a specific project.
     *
     * @param projectId the ID of the project for which to find vacancies
     * @return a ResponseEntity containing a list of VacancyResponseDto, with HTTP status 200 OK
     */
    @GetMapping("/{projectId}")
    public ResponseEntity<List<VacancyResponseDto>> findVacanciesByProjectId(@PathVariable Long projectId) {
        log.info("Found all vacancies with ProjectId {}", projectId);
        return ResponseEntity.ok(vacancyService.getVacanciesByProjectId(projectId));
    }

    /**
     * Creates a new vacancy for a specific project.
     *
     * @param projectId the ID of the project to associate with the vacancy
     * @param request the request body containing the vacancy data
     * @return a ResponseEntity containing the created VacancyResponseDto, with HTTP status 201 CREATED
     *         or HTTP status 404 NOT FOUND if the project is not found
     */
    @PostMapping("/{projectId}")
    public ResponseEntity<VacancyResponseDto> createVacancy(@PathVariable Long projectId,
                                                            @Valid @RequestBody VacancyRequestDto request) {
        log.info("The vacancy was created {}", request);
        try {
            VacancyResponseDto response = vacancyService.createVacancy(projectId, request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ProjectNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Updates an existing vacancy.
     *
     * @param vacancyId the ID of the vacancy to update
     * @param request the request body containing the updated vacancy data
     * @return a ResponseEntity containing the updated VacancyResponseDto, with HTTP status 200 OK
     */
    @PutMapping("/{vacancyId}")
    public ResponseEntity<VacancyResponseDto> updateVacancy(@PathVariable Long vacancyId,
                                                            @Valid @RequestBody VacancyRequestDto request) {
        log.info("The vacancy was updated {}", request);
        VacancyResponseDto updatedVacancy = vacancyService.updateVacancy(vacancyId, request);
        return ResponseEntity.ok(updatedVacancy);
    }

    /**
     * Deletes a specific vacancy.
     *
     * @param vacancyId the ID of the vacancy to delete
     * @return a ResponseEntity with HTTP status 204 NO CONTENT if the vacancy is deleted successfully
     */
    @DeleteMapping("/{vacancyId}")
    public ResponseEntity<Void> deleteVacancy(@PathVariable Long vacancyId) {
        log.info("Was deleted the vacancy with Id {}", vacancyId);
        vacancyService.deleteVacancy(vacancyId);
        return ResponseEntity.noContent().build();
    }
}
