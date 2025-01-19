package isaeva.TroodProjectsApp.repository;

import isaeva.TroodProjectsApp.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    List<Vacancy> findByProjectId(Long projectId);
}
