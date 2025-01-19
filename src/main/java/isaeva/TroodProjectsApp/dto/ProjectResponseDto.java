package isaeva.TroodProjectsApp.dto;

import java.util.List;

public record ProjectResponseDto(Long id, String name, String description, List<VacancyResponseDto> vacancies) {
}
