package com.example.Food.Delivery.Platform.Controllers;

import com.example.Food.Delivery.Platform.DTO.request.ReviewRequestDTO;
import com.example.Food.Delivery.Platform.DTO.response.AverageRatingDTO;
import com.example.Food.Delivery.Platform.DTO.response.ReviewResponseDTO;
import com.example.Food.Delivery.Platform.Services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    //RESTAURANT REVIEW
    @PostMapping("/restaurant/{restaurantId}/customer/{customerId}")
    public ResponseEntity<ReviewResponseDTO> leaveRestaurantReview(
            @PathVariable Integer restaurantId,
            @PathVariable Integer customerId,
            @RequestBody ReviewRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reviewService.leaveRestaurantReview(customerId, restaurantId, dto.getRating(), dto.getComment()));
    }

    //DRIVER REVIEW
    @PostMapping("/driver/{driverId}/customer/{customerId}")
    public ResponseEntity<ReviewResponseDTO> leaveDriverReview(
            @PathVariable Integer driverId,
            @PathVariable Integer customerId,
            @RequestBody ReviewRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reviewService.leaveDriverReview(customerId, driverId, dto.getRating(), dto.getComment()));
    }

    //RESTAURANT REVIEWS
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<Page<ReviewResponseDTO>> getRestaurantReviews(
            @PathVariable Integer restaurantId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        return ResponseEntity.ok(reviewService.getRestaurantReviews(restaurantId,page,size));
    }

    //DRIVER REVIEWS
    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<ReviewResponseDTO>> getDriverReviews(@PathVariable Integer driverId) {

        return ResponseEntity.ok(reviewService.getDriverReviews(driverId));
    }

    //SOFT DELETE REVIEW
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDTO> deleteReview(@PathVariable Integer reviewId) {

        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

    //Average for Restaurant
    @GetMapping("/restaurant/{restaurantId}/average")
    public ResponseEntity<AverageRatingDTO> getRestaurantAverage(@PathVariable Integer restaurantId){

        return ResponseEntity.ok(reviewService.getRestaurantAverage(restaurantId));
    }

    //Average for Driver
    @GetMapping("/driver/{driverId}/average")
    public ResponseEntity<AverageRatingDTO> getDriverAverage(@PathVariable Integer driverId){

        return ResponseEntity.ok(reviewService.getDriverAverage(driverId));
    }

    //Restaurant Reviews
    /*@GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<Page<ReviewResponseDTO>> getRestaurantReviews(
            @PathVariable Integer restaurantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){

        return ResponseEntity.ok(reviewService.getRestaurantReviews(restaurantId, page, size));
    }*/
}
