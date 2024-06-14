package br.com.gestaoeventos.config;

import br.com.gestaoeventos.domain.attendee.exceptions.AttendeeAlreadyRegisteredException;
import br.com.gestaoeventos.domain.attendee.exceptions.AttendeeNotFoundException;
import br.com.gestaoeventos.domain.checkin.exceptions.CheckInAlreadyExistsException;
import br.com.gestaoeventos.domain.event.exceptions.EventFullException;
import br.com.gestaoeventos.domain.event.exceptions.EventNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionEntityHandler {

    @ExceptionHandler(AttendeeNotFoundException.class)
    public ResponseEntity handleAttendeeNotFound(AttendeeNotFoundException attendeeNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(attendeeNotFoundException.getMessage());
    }

    @ExceptionHandler(AttendeeAlreadyRegisteredException.class)
    public ResponseEntity handleAttendeeAlreadyExists(AttendeeAlreadyRegisteredException attendeeAlreadyRegisteredException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(attendeeAlreadyRegisteredException.getMessage());
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity handleEventNotFound(EventNotFoundException eventNotFoundException) {
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(eventNotFoundException.getMessage());
    }

    @ExceptionHandler(EventFullException.class)
    public ResponseEntity handleEventFullException(EventFullException eventFullException) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(eventFullException.getMessage());
    }

    @ExceptionHandler(CheckInAlreadyExistsException.class)
    public ResponseEntity handleCheckInAlreadyExistsException(CheckInAlreadyExistsException checkInAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(checkInAlreadyExistsException.getMessage());
    }

}
