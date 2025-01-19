package isaeva.TroodProjectsApp.dto;

import jakarta.validation.constraints.NotBlank;

public record VacancyRequestDto(
        @NotBlank
        String title,
        @NotBlank
        String description
) {
}
