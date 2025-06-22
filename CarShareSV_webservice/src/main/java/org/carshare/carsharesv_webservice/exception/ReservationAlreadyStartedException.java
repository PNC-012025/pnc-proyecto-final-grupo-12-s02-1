package org.carshare.carsharesv_webservice.exception;

public class ReservationAlreadyStartedException extends RuntimeException {
    public ReservationAlreadyStartedException(String message) {
        super(message);
    }
}
