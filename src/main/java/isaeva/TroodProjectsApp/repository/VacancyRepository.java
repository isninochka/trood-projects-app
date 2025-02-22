package isaeva.TroodProjectsApp.repository;

import isaeva.TroodProjectsApp.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    List<Vacancy> findByProjectId(Long projectId);
}
