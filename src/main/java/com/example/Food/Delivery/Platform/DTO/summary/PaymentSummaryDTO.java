package com.example.Food.Delivery.Platform.DTO.summary;

import com.example.Food.Delivery.Platform.Entities.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSummaryDTO {
    private Integer id;
    private String paymentMethod;
    private String status;
    private Double amount;

    public static PaymentSummaryDTO fromEntity(Payment p) {
        return new PaymentSummaryDTO(
                p.getId(),
                p.getPaymentMethod(),
                p.getStatus(),
                p.getAmount()
        );
    }
}
