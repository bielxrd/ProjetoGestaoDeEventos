package br.com.gestaoeventos.dto.attendee;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AttendeeBadgeDTO {
    @Schema(example = "Gabriel Dias")
    String name;
    @Schema(example = "gabriel.dias@gmail.com")
    String email;
    String checkInUrl;
    String eventId;
}
