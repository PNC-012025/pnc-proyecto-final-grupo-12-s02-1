package org.carshare.carsharesv_webservice.exception;

import java.time.LocalDateTime;

public record CustomErrorResponse(
        LocalDateTime timestamp,
        String message,
        String path
) {
}
