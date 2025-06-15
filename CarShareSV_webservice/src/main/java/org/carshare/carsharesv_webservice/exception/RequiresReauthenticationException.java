package org.carshare.carsharesv_webservice.exception;

public class RequiresReauthenticationException extends RuntimeException {
    public RequiresReauthenticationException(String message) {
        super(message);
    }
}
