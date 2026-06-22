package com.example.Food.Delivery.Platform.DTO.response;

import com.example.Food.Delivery.Platform.Entities.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDTO {
    private Integer id;
    private String paymentMethod;
    private String status;
    private Double amount;
    private String transactionRef;
    private LocalDateTime processedAt;
    private Integer orderId;

    public static PaymentResponseDTO fromEntity(Payment pay) {
        if (pay == null) return null;
        PaymentResponseDTO dto = new PaymentResponseDTO();
        dto.setId(pay.getId());
        dto.setPaymentMethod(pay.getPaymentMethod());
        dto.setStatus(pay.getStatus());
        dto.setAmount(pay.getAmount());
        dto.setTransactionRef(pay.getTransactionRef());
        dto.setProcessedAt(pay.getProcessedAt());
        if (pay.getOrder() != null) {
            dto.setOrderId(pay.getOrder().getId());
        }
        return dto;
    }
}
