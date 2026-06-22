package com.example.Food.Delivery.Platform.DTO.response;

import com.example.Food.Delivery.Platform.Entities.Delivery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryResponseDTO {
    private Integer id;
    private String trackingCode;
    private String status;
    private LocalDateTime assignedAt;
    private LocalDateTime pickedUpAt;
    private LocalDateTime deliveredAt;
    private Integer orderId;
    private String orderCode;
    private String driverName;

    public static DeliveryResponseDTO fromEntity(Delivery del) {
        if (del == null) return null;
        DeliveryResponseDTO dto = new DeliveryResponseDTO();
        dto.setId(del.getId());
        dto.setTrackingCode(del.getTrackingCode());
        dto.setStatus(del.getStatus());
        dto.setAssignedAt(del.getAssignedAt());
        dto.setPickedUpAt(del.getPickedUpAt());
        dto.setDeliveredAt(del.getDeliveredAt());

        if (del.getOrder() != null) {
            dto.setOrderId(del.getOrder().getId());
            dto.setOrderCode(del.getOrder().getOrderCode());
        }
        if (del.getDriver() != null) {
            dto.setDriverName(del.getDriver().getFirstName() + " " + del.getDriver().getLastName());
        }
        return dto;
    }
}
