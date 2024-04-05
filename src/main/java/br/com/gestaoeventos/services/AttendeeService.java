package br.com.gestaoeventos.services;

import br.com.gestaoeventos.domain.attendee.Attendee;
import br.com.gestaoeventos.dto.attendee.AttendeesListResponseDTO;
import br.com.gestaoeventos.repositories.AttendeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendeeService {

    @Autowired
    private AttendeeRepository attendeeRepository;

    public List<Attendee> getAllAttendeesFromEvent(String eventId) {
        return this.attendeeRepository.findByEventId(eventId);
    }

    public AttendeesListResponseDTO getEventsAttendee(String eventId){
        List<Attendee> attendeeList = getAllAttendeesFromEvent(eventId);
    }

}
