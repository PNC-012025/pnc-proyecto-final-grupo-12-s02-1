package org.carshare.carsharesv_webservice.domain.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class ReviewResponseDTO {

    private UUID reviewId;

    private String comment;

    private Integer rating;

    private String reviewUsername;

    private UUID reviewCarId;

}
