package org.carshare.carsharesv_webservice.exception;

public class NotActiveUserException extends RuntimeException {
    public NotActiveUserException(String message) {
        super(message);
    }
}
