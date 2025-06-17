package org.carshare.carsharesv_webservice.exception;

public class ExistingCarException extends RuntimeException {
    public ExistingCarException(String message) {
        super(message);
    }
}
