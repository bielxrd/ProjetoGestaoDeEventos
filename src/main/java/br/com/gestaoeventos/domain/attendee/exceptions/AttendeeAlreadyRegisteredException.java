package br.com.gestaoeventos.domain.attendee.exceptions;

public class AttendeeAlreadyRegisteredException extends RuntimeException {

    public AttendeeAlreadyRegisteredException(String message) {
        super(message);
    }

}
