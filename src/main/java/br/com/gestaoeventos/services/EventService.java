package br.com.gestaoeventos.services;

import br.com.gestaoeventos.domain.attendee.Attendee;
import br.com.gestaoeventos.domain.event.Event;
import br.com.gestaoeventos.domain.event.exceptions.EventNotFoundException;
import br.com.gestaoeventos.dto.event.EventIdDTO;
import br.com.gestaoeventos.dto.event.EventRequestDTO;
import br.com.gestaoeventos.dto.event.EventResponseDTO;
import br.com.gestaoeventos.repositories.AttendeeRepository;
import br.com.gestaoeventos.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {

    @Autowired
    private final EventRepository eventRepository;

    @Autowired
    private final AttendeeRepository attendeeRepository;

    public EventResponseDTO getEventDetail(String eventId) {
        Event eventFind = this.eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found with ID: "+eventId));
        List<Attendee> attendeeList = this.attendeeRepository.findByEventId(eventId);
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


    private String createSlug(String text) {
        String normalize = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalize.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "").replaceAll("[^\\w\\s]", "").replaceAll("\\s+", "-").toLowerCase();
    }

}
