package br.com.gestaoeventos.dto.event;

public record EventRequestDTO(String title, String details, Integer maximumAttendees) {
}
