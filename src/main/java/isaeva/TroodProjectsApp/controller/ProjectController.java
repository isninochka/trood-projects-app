package isaeva.TroodProjectsApp.controller;

import isaeva.TroodProjectsApp.dto.ProjectDto;
import isaeva.TroodProjectsApp.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    /**
     * Retrieves a list of projects.
     *
     * @return a list of ProjectResponseDto, with HTTP status 200 OK
     */
    @GetMapping
    public List<ProjectResponseDto> findAllProjects() {
        log.info("Found all projects");
        return projectService.getAllProjects();
    }

    /**
     * Retrieves a specific project by its ID.
     *
     * @param id the ID of the project
     * @return a ResponseEntity containing the finding ProjectResponseDto, with HTTP status 200 OK
     *               or HTTP status 404 NOT FOUND if the project is not found
     */

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDto> findProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    /**
     * Creates a new project.
     *
     * @param request the request body containing the project data
     * @return a ResponseEntity containing the created ProjectResponseDto, with HTTP status 201 CREATED
     */
    @PostMapping
    public ResponseEntity<ProjectResponseDto> createProject(@Valid @RequestBody ProjectDto request) {
        log.info("The project was created {}", request);
        return ResponseEntity.ok(projectService.createProject(request));
    }

    /**
     * Updates an existing project.
     *
     * @param id the ID of the project to update
     * @param request the request body containing the project data
     * @return a ResponseEntity containing the updated ProjectResponseDto, with HTTP status 200 OK
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDto> updateProject(@PathVariable Long id,
                                                            @Valid @RequestBody ProjectDto request) {
        log.info("The project was updated {}", request);
        return ResponseEntity.ok(projectService.updateProject(id, request));
    }

    /**
     * Deletes a specific project.
     *
     * @param id the ID of the project to delete
     * @return a ResponseEntity with HTTP status 204 NO CONTENT if the project is deleted successfully
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        log.info("Was deleted the project with Id {}", id);
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

}
