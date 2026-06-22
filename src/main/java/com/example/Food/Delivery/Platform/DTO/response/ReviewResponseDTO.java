package com.example.Food.Delivery.Platform.DTO.response;

import com.example.Food.Delivery.Platform.Entities.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDTO {
    private Integer id;
    private String targetType;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
    private String customerName;

    public static ReviewResponseDTO fromEntity(Review review) {
        if (review == null) return null;

        ReviewResponseDTO dto = new ReviewResponseDTO();

        dto.setId(review.getId());
        dto.setTargetType(review.getTargetType());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setCreatedAt(review.getCreatedAt());
        if (review.getCustomer() != null) {
            dto.setCustomerName(review.getCustomer().getFirstName() + " " + review.getCustomer().getLastName());
        }
        return dto;
    }
}
