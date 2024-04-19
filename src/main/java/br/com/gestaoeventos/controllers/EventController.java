package br.com.gestaoeventos.controllers;

import br.com.gestaoeventos.domain.event.Event;
import br.com.gestaoeventos.dto.attendee.AttendeeIdDTO;
import br.com.gestaoeventos.dto.attendee.AttendeeRequestDTO;
import br.com.gestaoeventos.dto.attendee.AttendeesListResponseDTO;
import br.com.gestaoeventos.dto.event.EventIdDTO;
import br.com.gestaoeventos.dto.event.EventRequestDTO;
import br.com.gestaoeventos.dto.event.EventResponseDTO;
import br.com.gestaoeventos.services.AttendeeService;
import br.com.gestaoeventos.services.EventService;
import com.sun.java.accessibility.util.EventID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    @Autowired
    private final EventService eventService;

    @Autowired
    private final AttendeeService attendeeService;

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String id) {
        EventResponseDTO eventDetail = eventService.getEventDetail(id);
        return ResponseEntity.ok().body(eventDetail);
    }

    @PostMapping("/create")
    public ResponseEntity<EventIdDTO> createEvent(@RequestBody EventRequestDTO eventRequest, UriComponentsBuilder uriComponentsBuilder) {
        EventIdDTO eventId = this.eventService.createEvent(eventRequest);

        URI uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(eventId.eventId()).toUri();

        return ResponseEntity.created(uri).body(eventId);
    }

    @GetMapping("/attendees/{id}")
    public ResponseEntity<AttendeesListResponseDTO> getEventAttendees(@PathVariable String id) {
        AttendeesListResponseDTO eventsAttendee = this.attendeeService.getEventsAttendee(id);
        return ResponseEntity.ok(eventsAttendee);
    }

    @PostMapping("/{eventId}/attendees")
    public ResponseEntity<AttendeeIdDTO> registerParticipant(@PathVariable String eventId, @RequestBody AttendeeRequestDTO attendeeRequestDTO, UriComponentsBuilder uriComponentsBuilder) {
        AttendeeIdDTO attendeeIdDTO = this.eventService.registerAttendeeOnEvent(eventId, attendeeRequestDTO);
        URI uri = uriComponentsBuilder.path("/attendees/{attendeeId}/badge").buildAndExpand(attendeeIdDTO.attendeeId()).toUri();

        return ResponseEntity.created(uri).body(attendeeIdDTO);
    }

}
