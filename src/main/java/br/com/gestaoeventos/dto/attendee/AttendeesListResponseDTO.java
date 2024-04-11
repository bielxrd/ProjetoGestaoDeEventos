package br.com.gestaoeventos.dto.attendee;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AttendeesListResponseDTO {
    List<AttendeeDetails> attendees;
}
