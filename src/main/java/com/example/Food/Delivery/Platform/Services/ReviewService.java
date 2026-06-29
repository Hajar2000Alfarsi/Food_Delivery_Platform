package com.example.Food.Delivery.Platform.Services;

import com.example.Food.Delivery.Platform.DTO.response.AverageRatingDTO;
import com.example.Food.Delivery.Platform.DTO.response.ReviewResponseDTO;
import com.example.Food.Delivery.Platform.Entities.Customer;
import com.example.Food.Delivery.Platform.Entities.DeliveryDriver;
import com.example.Food.Delivery.Platform.Entities.Restaurant;
import com.example.Food.Delivery.Platform.Entities.Review;
import com.example.Food.Delivery.Platform.Exceptions.ResourceNotFoundException;
import com.example.Food.Delivery.Platform.Repositories.CustomerRepository;
import com.example.Food.Delivery.Platform.Repositories.DriverRepository;
import com.example.Food.Delivery.Platform.Repositories.RestaurantRepository;
import com.example.Food.Delivery.Platform.Repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final DriverRepository driverRepository;

    @Autowired

    public ReviewService(ReviewRepository reviewRepository, CustomerRepository customerRepository, RestaurantRepository restaurantRepository, DriverRepository driverRepository) {
        this.reviewRepository = reviewRepository;
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
        this.driverRepository = driverRepository;
    }

    //RESTAURANT REVIEW
    public ReviewResponseDTO leaveRestaurantReview(Integer customerId, Integer restaurantId, int rating, String comment) {
        Customer customer = customerRepository.findByActiveId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Restaurant restaurant = restaurantRepository.findByActiveId(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        Review review = new Review();

        review.setCustomer(customer);
        review.setRestaurant(restaurant);
        review.setRating(rating);
        review.setComment(comment);
        review.setTargetType("RESTAURANT");
        review.setCreatedAt(LocalDateTime.now());
        review.setIsActive(true);

        return ReviewResponseDTO.fromEntity(reviewRepository.save(review));
    }

    //DRIVER REVIEW
    public ReviewResponseDTO leaveDriverReview(Integer customerId, Integer driverId, int rating, String comment) {
        Customer customer = customerRepository.findByActiveId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        DeliveryDriver driver = driverRepository.findByActiveID(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));

        Review review = new Review();

        review.setCustomer(customer);
        review.setDriver(driver);
        review.setRating(rating);
        review.setComment(comment);
        review.setTargetType("DRIVER");
        review.setCreatedAt(LocalDateTime.now());
        review.setIsActive(true);

        return ReviewResponseDTO.fromEntity(reviewRepository.save(review));
    }

    //GET RESTAURANT REVIEWS
    public Page<ReviewResponseDTO> getRestaurantReviews(Integer restaurantId, Integer page, Integer size) {
        restaurantRepository.findByActiveId(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        if (page == null || size == null) {
            page = 0;
            size = Integer.MAX_VALUE; // return all values
        }

        Pageable pageable = PageRequest.of(page, size);

        return reviewRepository.getRestaurantReviews(restaurantId, pageable)
                .map(ReviewResponseDTO::fromEntity);
    }

    //GET DRIVER REVIEWS
    public List<ReviewResponseDTO> getDriverReviews(Integer driverId) {

        return reviewRepository.findByDriverId(driverId)
                .stream()
                .map(ReviewResponseDTO::fromEntity)
                .toList();
    }

    //SOFT DELETE REVIEW
    public void deleteReview(Integer reviewId) {

        Review review = reviewRepository.findByActiveId(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        review.setIsActive(false);
        review.setUpdatedDate(LocalDateTime.now());

        reviewRepository.save(review);
    }

    //Average for Restaurant
    public AverageRatingDTO getRestaurantAverage(Integer restaurantId) {
        restaurantRepository.findByActiveId(restaurantId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Restaurant not found"));

        Double avg = reviewRepository.getRestaurantAverage(restaurantId);

        if (avg == null) {
            avg = 0.0;
        }

        return new AverageRatingDTO(avg);
    }

    //Average for Driver
    public AverageRatingDTO getDriverAverage(Integer driverId){
        driverRepository.findByActiveID(driverId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Driver not found"));

        Double avg = reviewRepository.getDriverAverage(driverId);

        if (avg == null){
            avg = 0.0;
        }

        return new AverageRatingDTO(avg);
    }

    //Restaurant Reviews
    /*public Page<ReviewResponseDTO> getRestaurantReviews(Integer restaurantId, int page, int size){
        restaurantRepository.findByActiveId(restaurantId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Restaurant not found"));

        Pageable pageable = PageRequest.of(page,size);

        return reviewRepository.getRestaurantReviews(restaurantId, pageable)
                .map(ReviewResponseDTO::fromEntity);
    }*/
}
