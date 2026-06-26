package com.example.Food.Delivery.Platform.Controllers;

import com.example.Food.Delivery.Platform.DTO.request.ReviewRequestDTO;
import com.example.Food.Delivery.Platform.DTO.response.ReviewResponseDTO;
import com.example.Food.Delivery.Platform.Services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


}
