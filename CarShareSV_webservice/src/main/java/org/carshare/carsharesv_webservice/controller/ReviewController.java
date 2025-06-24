package org.carshare.carsharesv_webservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.carshare.carsharesv_webservice.domain.dto.GenericResponse;
import org.carshare.carsharesv_webservice.domain.dto.request.CreateReviewDTO;
import org.carshare.carsharesv_webservice.domain.dto.response.ReviewResponseDTO;
import org.carshare.carsharesv_webservice.service.iReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.carshare.carsharesv_webservice.util.Constants.*;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(API + REVIEWS_CONTROLLER)
public class ReviewController {

    private final iReviewService reviewService;

    //Admin Endpoint
    @GetMapping(GET_ALL)
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN')")
    public ResponseEntity<GenericResponse> getAllReviews() {
        List<ReviewResponseDTO> data = reviewService.getAllReviews();
        return GenericResponse.builder()
                .data(data)
                .status(HttpStatus.OK)
                .build().buildResponse();
    }


    @PostMapping(CREATE + "/{carId}")
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<GenericResponse> saveReview(@RequestBody @Valid CreateReviewDTO body, @PathVariable("carId") UUID cardId) {
        ReviewResponseDTO data = reviewService.saveReview(body, cardId);
        return GenericResponse.builder()
                .message("Review saved Successfully")
                .data(data)
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @GetMapping(GET_REVIEW_BY_ID + "/{reviewId}")
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<GenericResponse> getReviewById(@PathVariable("reviewId") UUID reviewId) {
        ReviewResponseDTO data = reviewService.getReviewById(reviewId);
        return GenericResponse.builder()
                .data(data)
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @GetMapping(GET_ALL_REVIEW_BY_USER_ID + "/{userName}")
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<GenericResponse> getAllReviewsByUserName(@PathVariable("userName") String userName) {
        List<ReviewResponseDTO> data = reviewService.getReviewByUserUsername(userName);
        return GenericResponse.builder()
                .data(data)
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @GetMapping(GET_ALL_REVIEW_BY_CAR_ID + "/{carId}")
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<GenericResponse> getAllReviewsByUserName(@PathVariable("carId") UUID carId) {
        List<ReviewResponseDTO> data = reviewService.getReviewByCarCarId(carId);
        return GenericResponse.builder()
                .data(data)
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @PatchMapping(UPDATE_COMMENT)
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<GenericResponse> updateReviewComment(@RequestParam("reviewId") UUID reviewId, @RequestBody @Valid String comment) {
        ReviewResponseDTO data = reviewService.updateReviewComment(reviewId, comment);
        return GenericResponse.builder()
                .data(data)
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @PatchMapping(UPDATE_RATING)
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<GenericResponse> updateReviewComment(@RequestParam("reviewId") UUID reviewId, @RequestParam("rating") Integer rating) {
        ReviewResponseDTO data = reviewService.updateReviewRating(reviewId, rating);
        return GenericResponse.builder()
                .data(data)
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

    @DeleteMapping(DELETE)
    @PreAuthorize("hasAnyRole('SYSADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<GenericResponse> deleteReview(@RequestParam("reviewId") UUID reviewId) {
        reviewService.deleteReviewById(reviewId);
        return GenericResponse.builder()
                .message("Review deleted Successfully")
                .data(null)
                .status(HttpStatus.OK)
                .build().buildResponse();
    }

}
