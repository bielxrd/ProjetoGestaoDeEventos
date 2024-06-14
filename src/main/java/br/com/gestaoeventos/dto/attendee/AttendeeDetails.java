package br.com.gestaoeventos.dto.attendee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendeeDetails {

    private String id;
    private String name;
    private String email;

    private LocalDateTime createdAt;

    LocalDateTime checkedInAt;

}
