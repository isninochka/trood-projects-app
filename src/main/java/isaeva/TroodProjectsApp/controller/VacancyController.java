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

    @GetMapping("/{projectId}")
    public ResponseEntity<List<VacancyResponseDto>> findVacanciesByProjectId(@PathVariable Long projectId) {
        log.info("getVacanciesByProjectId {}", projectId);
        return ResponseEntity.ok(vacancyService.getVacanciesByProjectId(projectId));
    }

    @PostMapping("/{projectId}")
    public ResponseEntity<VacancyResponseDto> createVacancy(@PathVariable Long projectId,
                                                            @Valid @RequestBody VacancyRequestDto request) {
        log.info("createVacancy {}", request);
        try {
            VacancyResponseDto response = vacancyService.createVacancy(projectId, request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ProjectNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{vacancyId}")
    public ResponseEntity<VacancyResponseDto> updateVacancy(@PathVariable Long vacancyId,
                                                            @Valid @RequestBody VacancyRequestDto request) {
        log.info("updateVacancy {}", request);
        VacancyResponseDto updatedVacancy = vacancyService.updateVacancy(vacancyId, request);
        return ResponseEntity.ok(updatedVacancy);
    }

    @DeleteMapping("/{vacancyId}")
    public ResponseEntity<Void> deleteVacancy(@PathVariable Long vacancyId) {
        log.info("deleteVacancy {}", vacancyId);
        vacancyService.deleteVacancy(vacancyId);
        return ResponseEntity.noContent().build();
    }
}
