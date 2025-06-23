package org.carshare.carsharesv_webservice.service.implementation;

import lombok.RequiredArgsConstructor;
import org.carshare.carsharesv_webservice.domain.dto.request.CreateReviewDTO;
import org.carshare.carsharesv_webservice.domain.dto.response.ReviewResponseDTO;
import org.carshare.carsharesv_webservice.domain.entity.Car;
import org.carshare.carsharesv_webservice.domain.entity.Review;
import org.carshare.carsharesv_webservice.exception.NotAllowedOperationException;
import org.carshare.carsharesv_webservice.exception.ResourceNotFoundException;
import org.carshare.carsharesv_webservice.repository.iCarRepository;
import org.carshare.carsharesv_webservice.repository.iReviewRepository;
import org.carshare.carsharesv_webservice.service.iReviewService;
import org.carshare.carsharesv_webservice.util.Constants;
import org.carshare.carsharesv_webservice.util.CurrentUserInfo;
import org.carshare.carsharesv_webservice.util.UsefullMethods;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Validated
public class ReviewServiceImpl implements iReviewService {
    private final iReviewRepository reviewRepository;
    private final ModelMapper modelMapper;
    private final iCarRepository carRepository;
    private final UsefullMethods usefullMethods;

    @Override
    public ReviewResponseDTO saveReview(CreateReviewDTO reviewDTO, UUID carId){
        CurrentUserInfo userInfo = usefullMethods.getUserInfo(null);
        Car car = carRepository.findCarByCarId(carId).orElse(null);

        Review newReview = new Review();

        newReview.setComment(reviewDTO.getComment());
        newReview.setRating(reviewDTO.getRating());
        newReview.setUser(userInfo.currentUser());
        newReview.setCar(car);

        reviewRepository.save(newReview);

        return modelMapper.map(newReview, ReviewResponseDTO.class);

    }

    public List<ReviewResponseDTO> getAllReviews(){
        List<ReviewResponseDTO> reviews = reviewRepository.findAll().stream().map(review -> modelMapper.map(review, ReviewResponseDTO.class)).toList();
        if(reviews.isEmpty()) throw new ResourceNotFoundException("Reviews not found");
        return reviews;
    }

    public ReviewResponseDTO getReviewById(UUID id) {
        Review review = reviewRepository.findById(id).orElse(null);
        if (review == null) throw new ResourceNotFoundException("Review not found");
        return modelMapper.map(review, ReviewResponseDTO.class);
    }

    public List<ReviewResponseDTO> getReviewByCarCarId(UUID carId) {
        List<ReviewResponseDTO> reviews = reviewRepository.findReviewByCarCarId(carId).stream().map(review -> modelMapper.map(review, ReviewResponseDTO.class)).toList();

        if(reviews.isEmpty()) throw new ResourceNotFoundException("Car have not reviews");

        return reviews;
    }

    public List<ReviewResponseDTO> getReviewByUserUsername(String username) {
        List<ReviewResponseDTO> reviews = reviewRepository.findReviewByUserUsername(username).stream().map(review -> modelMapper.map(review, ReviewResponseDTO.class)).toList();

        if(reviews.isEmpty()) throw new ResourceNotFoundException("Car have not reviews");

        return reviews;
    }

    @Override
    public ReviewResponseDTO updateReviewComment(UUID reviewId, String comment) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        CurrentUserInfo userInfo = usefullMethods.getUserInfo(null);
        if (review == null) throw new ResourceNotFoundException("Review not found");

        if(userInfo.roles().contains(Constants.SYSADMIN) || userInfo.roles().contains(Constants.ADMIN) || review.getUser().getUserId().equals(userInfo.currentUser().getUserId())) {
            review.setComment(comment);
            reviewRepository.save(review);
        } else throw new NotAllowedOperationException("You don't have permissions to update this review. You can only update your own review");


        return modelMapper.map(review, ReviewResponseDTO.class);
    }

    @Override
    public ReviewResponseDTO updateReviewRating(UUID reviewId, Integer rating) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        CurrentUserInfo userInfo = usefullMethods.getUserInfo(null);
        if (review == null) throw new ResourceNotFoundException("Review not found");

        if(userInfo.roles().contains(Constants.SYSADMIN) || userInfo.roles().contains(Constants.ADMIN) || review.getUser().getUserId().equals(userInfo.currentUser().getUserId())) {
            review.setRating(rating);
            reviewRepository.save(review);
        } else throw new NotAllowedOperationException("You don't have permissions to update this review. You can only update your own review");

        return modelMapper.map(review, ReviewResponseDTO.class);

    }

    @Override
    public void deleteReviewById(UUID reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        CurrentUserInfo userInfo = usefullMethods.getUserInfo(null);
        if (review == null) throw new ResourceNotFoundException("Review not found");

        if(userInfo.roles().contains(Constants.SYSADMIN) || userInfo.roles().contains(Constants.ADMIN) || review.getUser().getUserId().equals(userInfo.currentUser().getUserId())) {
            reviewRepository.delete(review);
        } else throw new NotAllowedOperationException("You don't have permissions to update this review. You can only update your own review");
    }
}
