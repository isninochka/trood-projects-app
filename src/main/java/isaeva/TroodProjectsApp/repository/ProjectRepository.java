package isaeva.TroodProjectsApp.repository;

import isaeva.TroodProjectsApp.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
