package isaeva.TroodProjectsApp.dto;

import jakarta.validation.constraints.NotBlank;

public record ProjectRequestDto(
        @NotBlank
        String name,
        @NotBlank
        String description) {
}
