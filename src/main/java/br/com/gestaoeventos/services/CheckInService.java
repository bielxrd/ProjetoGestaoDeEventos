package br.com.gestaoeventos.services;

import br.com.gestaoeventos.domain.attendee.Attendee;
import br.com.gestaoeventos.domain.checkin.CheckIn;
import br.com.gestaoeventos.domain.checkin.exceptions.CheckInAlreadyExistsException;
import br.com.gestaoeventos.repositories.CheckinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckInService {

    @Autowired
    private final CheckinRepository checkinRepository;

    public void registerCheckIn(Attendee attendee) {

        this.verifyCheckInExists(attendee.getId());

        CheckIn newCheckIn = new CheckIn();
        newCheckIn.setAttendee(attendee);
        newCheckIn.setCreatedAt(LocalDateTime.now());
        this.checkinRepository.save(newCheckIn);
    }

    public Optional<CheckIn> getCheckIn(String attendeeId) {
        return this.checkinRepository.findByAttendeeId(attendeeId);
    }

    private void verifyCheckInExists(String attendeeId) {
        Optional<CheckIn> isCheckedIn = this.checkinRepository.findByAttendeeId(attendeeId);

        if (isCheckedIn.isPresent()) throw new CheckInAlreadyExistsException("Attendee Already Checked In");

    }

}
