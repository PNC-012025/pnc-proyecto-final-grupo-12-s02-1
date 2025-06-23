package org.carshare.carsharesv_webservice.repository;

import org.carshare.carsharesv_webservice.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface iReviewRepository extends JpaRepository<Review, UUID> {

    Optional<Review> findReviewByReviewId(UUID reviewId);
    List<Review> findAll();
    List<Review> findReviewByUserUsername(String username);
    List<Review> findReviewByCarCarId(UUID carId);

}
