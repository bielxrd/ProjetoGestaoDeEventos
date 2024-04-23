package br.com.gestaoeventos.controllers;

import br.com.gestaoeventos.dto.attendee.AttendeeBadgeResponseDTO;
import br.com.gestaoeventos.services.AttendeeService;
import br.com.gestaoeventos.services.CheckInService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/attendees")
@RequiredArgsConstructor
public class AttendeeController {

    @Autowired
    private final AttendeeService attendeeService;



    @GetMapping("/{attendeeId}/badge")
    public ResponseEntity<AttendeeBadgeResponseDTO> getAttendeeBadge(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder) {
        AttendeeBadgeResponseDTO attendeeBadge = this.attendeeService.getAttendeeBadge(attendeeId, uriComponentsBuilder);
        return ResponseEntity.ok(attendeeBadge);
    }

    @PostMapping("/{attendeeId}/check-in")
    public ResponseEntity doCheckIn(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder) {
        this.attendeeService.checkInAttendee(attendeeId);

        URI uri = uriComponentsBuilder.path("/atendees/{attendeeId}/badge").buildAndExpand(attendeeId).toUri();

        return ResponseEntity.created(uri).build();
    }
}
