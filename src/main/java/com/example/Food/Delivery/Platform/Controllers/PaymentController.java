package com.example.Food.Delivery.Platform.Controllers;

import com.example.Food.Delivery.Platform.DTO.response.PaymentAnalyticsDTO;
import com.example.Food.Delivery.Platform.DTO.response.PaymentResponseDTO;
import com.example.Food.Delivery.Platform.DTO.summary.PaymentSummaryDTO;
import com.example.Food.Delivery.Platform.Services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
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

    //Complete Payment
    @PutMapping("/{paymentId}/complete")
    public ResponseEntity<PaymentResponseDTO> completePayment(@PathVariable Integer paymentId) {
        return ResponseEntity.ok(paymentService.completePayment(paymentId));
    }

    //refund payment
    @PutMapping("/{paymentId}/refund")
    public ResponseEntity<PaymentResponseDTO> refundPayment(@PathVariable Integer paymentId) {
        return ResponseEntity.ok(paymentService.refundPayment(paymentId));
    }

    //Get Payment By Order
    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponseDTO> getPaymentByOrder(@PathVariable Integer orderId) {
        return ResponseEntity.ok(paymentService.getPaymentByOrder(orderId));
    }

    //get payments(filter payment)
    @GetMapping
    public ResponseEntity<List<PaymentSummaryDTO>> getPayments(
            @RequestParam(required = false) String method,
            @RequestParam(required = false) String status,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime from,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(paymentService.getPayments(method, status, from, to, pageable));
    }

    //Analytics by payment method
    @GetMapping("/analytics/by-method")
    public ResponseEntity<List<PaymentAnalyticsDTO>> getAnalytics() {

        return ResponseEntity.ok(paymentService.getAnalyticsByMethod());
    }
}
