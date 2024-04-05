package br.com.gestaoeventos.controllers;

import br.com.gestaoeventos.domain.event.Event;
import br.com.gestaoeventos.dto.event.EventIdDTO;
import br.com.gestaoeventos.dto.event.EventRequestDTO;
import br.com.gestaoeventos.dto.event.EventResponseDTO;
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

}
