package br.com.gestaoeventos.dto.attendee;

import java.time.LocalDateTime;

public record AttendeeDetails(String id, String name, String email, LocalDateTime createdAt, LocalDateTime checkedInAt) {
}
