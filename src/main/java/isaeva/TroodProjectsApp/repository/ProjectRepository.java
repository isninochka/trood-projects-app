package isaeva.TroodProjectsApp.repository;

import isaeva.TroodProjectsApp.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
