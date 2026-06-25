package com.example.Food.Delivery.Platform.Controllers;

import com.example.Food.Delivery.Platform.DTO.response.PaymentResponseDTO;
import com.example.Food.Delivery.Platform.Services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PostMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    //create payment
    @PostMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponseDTO> processPayment(
            @PathVariable Integer orderId,
            @RequestParam String method) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.processPayment(orderId, method));
    }


}
