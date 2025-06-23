package org.carshare.carsharesv_webservice.service;

import jakarta.validation.constraints.*;
import org.carshare.carsharesv_webservice.domain.dto.request.CreateReviewDTO;
import org.carshare.carsharesv_webservice.domain.dto.response.ReviewResponseDTO;
import org.hibernate.validator.constraints.Length;

import java.util.List;
import java.util.UUID;

public interface iReviewService {
    ReviewResponseDTO saveReview(CreateReviewDTO reviewDTO, UUID carId);
    List<ReviewResponseDTO> getAllReviews();
    List<ReviewResponseDTO> getReviewByUserUsername(String username);
    List<ReviewResponseDTO> getReviewByCarCarId(UUID carId);
    ReviewResponseDTO getReviewById(UUID reviewId);
    ReviewResponseDTO updateReviewRating(UUID reviewId, @NotNull @Min(0) @Max(5) Integer rating);
    ReviewResponseDTO updateReviewComment(UUID reviewId, @NotEmpty @NotBlank @Length(min = 10, max = 255) String comment);
    void deleteReviewById(UUID reviewId);

}

