package br.com.gestaoeventos.dto.attendee;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AttendeeBadgeDTO {
    String name;
    String email;
    String checkInUrl;
    String eventId;
}
