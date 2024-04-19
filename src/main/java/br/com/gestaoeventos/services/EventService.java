package br.com.gestaoeventos.services;

import br.com.gestaoeventos.domain.attendee.Attendee;
import br.com.gestaoeventos.domain.event.Event;
import br.com.gestaoeventos.domain.event.exceptions.EventFullException;
import br.com.gestaoeventos.domain.event.exceptions.EventNotFoundException;
import br.com.gestaoeventos.dto.attendee.AttendeeIdDTO;
import br.com.gestaoeventos.dto.attendee.AttendeeRequestDTO;
import br.com.gestaoeventos.dto.event.EventIdDTO;
import br.com.gestaoeventos.dto.event.EventRequestDTO;
import br.com.gestaoeventos.dto.event.EventResponseDTO;
import br.com.gestaoeventos.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {

    @Autowired
    private final EventRepository eventRepository;

    @Autowired
    private final AttendeeService attendeeService;

    public EventResponseDTO getEventDetail(String eventId) {
        Event eventFind = this.getEventById(eventId);
        List<Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(eventId);
        return new EventResponseDTO(eventFind, attendeeList.size());
    }

    public EventIdDTO createEvent(EventRequestDTO eventRequest) {
        Event event = new Event();
        event.setTitle(eventRequest.title());
        event.setDetails(eventRequest.details());
        event.setMaximumAttendees(eventRequest.maximumAttendees());
        event.setSlug(this.createSlug(eventRequest.title()));

        this.eventRepository.save(event);

        return new EventIdDTO(event.getId());
    }

    public AttendeeIdDTO registerAttendeeOnEvent(String eventId, AttendeeRequestDTO attendeeRequestDTO) {
        this.attendeeService.verifyAttendeeSubscription(attendeeRequestDTO.email(), eventId);

        Event eventFind = this.getEventById(eventId);
        List<Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(eventId);

        if (eventFind.getMaximumAttendees() <= attendeeList.size())
            throw new EventFullException("Event is already full and cannot add more attendees.");

        Attendee newAttendee = new Attendee();
        newAttendee.setName(attendeeRequestDTO.name());
        newAttendee.setEmail(attendeeRequestDTO.email());
        newAttendee.setEvent(eventFind);
        newAttendee.setCreatedAt(LocalDateTime.now());

        this.attendeeService.registerAttendee(newAttendee);

        return new AttendeeIdDTO(newAttendee.getId());
    }

    private Event getEventById(String eventId) {
        return this.eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + eventId));
    }



    private String createSlug(String text) {
        String normalize = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalize.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "").replaceAll("[^\\w\\s]", "").replaceAll("\\s+", "-").toLowerCase();
    }

}
