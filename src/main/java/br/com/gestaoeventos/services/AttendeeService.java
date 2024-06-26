package br.com.gestaoeventos.services;

import br.com.gestaoeventos.domain.attendee.Attendee;
import br.com.gestaoeventos.domain.attendee.exceptions.AttendeeAlreadyRegisteredException;
import br.com.gestaoeventos.domain.attendee.exceptions.AttendeeNotFoundException;
import br.com.gestaoeventos.domain.checkin.CheckIn;
import br.com.gestaoeventos.dto.attendee.*;
import br.com.gestaoeventos.repositories.AttendeeRepository;
import br.com.gestaoeventos.repositories.CheckinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {

    @Autowired
    private AttendeeRepository attendeeRepository;

    @Autowired
    private final CheckInService checkInService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Attendee createAttendee(NewAttendeeRequestDTO requestDTO) {
        Attendee attendee = new Attendee();
        attendee.setName(requestDTO.getName());
        attendee.setEmail(requestDTO.getEmail());
        attendee.setSenha(passwordEncoder.encode(requestDTO.getSenha()));
       return this.attendeeRepository.save(attendee);
    }

    public List<Attendee> getAllAttendeesFromEvent(String eventId) {
        return this.attendeeRepository.findByEventId(eventId);
    }

    public AttendeesListResponseDTO getAllAttendees() {
        List<Attendee> attendeeList = this.attendeeRepository.findAll();

        List<AttendeeDetails> attendeeDetailsList = attendeeList.stream().map(attendee -> {

            Optional<CheckIn> checkIn = this.checkInService.getCheckIn(attendee.getId());

            LocalDateTime checkedInAt = checkIn.isPresent() ? checkIn.get().getCreatedAt() : null;

            return new AttendeeDetails(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(), checkedInAt);
        }).toList();

        return new AttendeesListResponseDTO(attendeeDetailsList);
    }

    public AttendeesListResponseDTO getEventsAttendee(String eventId){
        List<Attendee> attendeeList = getAllAttendeesFromEvent(eventId);

       List<AttendeeDetails> attendeeDetailsList = attendeeList.stream().map(attendee -> {
           Optional<CheckIn> checkIn = this.checkInService.getCheckIn(attendee.getId());

           LocalDateTime checkedInAt = checkIn.isPresent() ? checkIn.get().getCreatedAt() : null;
           return new AttendeeDetails(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(), checkedInAt);
       }).toList();

        return new AttendeesListResponseDTO(attendeeDetailsList);
    }

    public void verifyAttendeeSubscription(String email, String eventId) {
        Optional<Attendee> verifiedAttendee = this.attendeeRepository.findByEventIdAndEmail(eventId, email);

        if (verifiedAttendee.isPresent()) throw new AttendeeAlreadyRegisteredException("Attendee is already registered");

    }

    public Attendee registerAttendee(Attendee newAttendee) {
        this.attendeeRepository.save(newAttendee);
        return newAttendee;
    }

    public AttendeeBadgeResponseDTO getAttendeeBadge(String attendeeId, UriComponentsBuilder uriComponentsBuilder) {
        Attendee attendee = this.getAttendee(attendeeId);
        URI uri = uriComponentsBuilder.path("/attendees/{attendeeId}/check-in").buildAndExpand(attendeeId).toUri();
        return new AttendeeBadgeResponseDTO(new AttendeeBadgeDTO(attendee.getName(), attendee.getEmail(), uri.toString(), attendee.getEvent().getId()));
    }

    public void checkInAttendee(String attendeeId) {
        Attendee attendee = this.getAttendee(attendeeId);
        this.checkInService.registerCheckIn(attendee);
        return;
    }

    private Attendee getAttendee(String attendeeId) {
        return this.attendeeRepository.findById(attendeeId).orElseThrow(() -> new AttendeeNotFoundException("Attendee not found with ID: "+attendeeId));
    }

}
