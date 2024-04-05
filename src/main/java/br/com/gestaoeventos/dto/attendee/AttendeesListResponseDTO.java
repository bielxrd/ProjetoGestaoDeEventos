package br.com.gestaoeventos.dto.attendee;

import lombok.Getter;

import java.util.List;

@Getter
public class AttendeesListResponseDTO {
    List<AttendeeDetails> attendees;
}
