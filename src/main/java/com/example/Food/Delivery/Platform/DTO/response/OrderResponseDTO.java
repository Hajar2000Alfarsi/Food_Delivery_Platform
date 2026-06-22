package com.example.Food.Delivery.Platform.DTO.response;

import com.example.Food.Delivery.Platform.DTO.summary.CustomerSummaryDTO;
import com.example.Food.Delivery.Platform.DTO.summary.RestaurantSummaryDTO;
import com.example.Food.Delivery.Platform.Entities.FoodOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    private Integer id;
    private String orderCode;
    private LocalDateTime orderDate;
    private String status;
    private Double subtotal;
    private Double deliveryFee;
    private Double discountAmount;
    private Double totalAmount;
    private String deliveryNotes;

    private CustomerSummaryDTO customer;
    private RestaurantSummaryDTO restaurant;
    private List<OrderItemResponseDTO> items;

    public static OrderResponseDTO fromEntity(FoodOrder order) {
        if (order == null) return null;
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setOrderCode(order.getOrderCode());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());
        dto.setSubtotal(order.getSubtotal());
        dto.setDeliveryFee(order.getDeliveryFee());
        dto.setDiscountAmount(order.getDiscountAmount());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setDeliveryNotes(order.getDeliveryNotes());

        dto.setCustomer(CustomerSummaryDTO.fromEntity(order.getCustomer()));
        dto.setRestaurant(RestaurantSummaryDTO.fromEntity(order.getRestaurant()));

        if (order.getOrderItems() != null) {
            dto.setItems(order.getOrderItems().stream()
                    .map(OrderItemResponseDTO::fromEntity)
                    .collect(Collectors.toList()));
        }
        return dto;
        }
}
