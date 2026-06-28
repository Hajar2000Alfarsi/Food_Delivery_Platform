package com.example.Food.Delivery.Platform.Services;

import com.example.Food.Delivery.Platform.DTO.response.PaymentAnalyticsDTO;
import com.example.Food.Delivery.Platform.DTO.response.PaymentResponseDTO;
import com.example.Food.Delivery.Platform.DTO.summary.PaymentSummaryDTO;
import com.example.Food.Delivery.Platform.Entities.FoodOrder;
import com.example.Food.Delivery.Platform.Entities.Payment;
import com.example.Food.Delivery.Platform.Exceptions.ResourceNotFoundException;
import com.example.Food.Delivery.Platform.Repositories.OrderRepository;
import com.example.Food.Delivery.Platform.Repositories.PaymentRepository;
import com.example.Food.Delivery.Platform.Utils.HelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Autowired

    public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    //process payment
    public PaymentResponseDTO processPayment(Integer orderId, String method) {
        FoodOrder order = orderRepository.findByActiveId(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        Payment payment = new Payment();

        payment.setPaymentMethod(method);
        payment.setStatus("PENDING");
        payment.setTransactionRef(HelperUtils.generateCode("PAY"));

        payment.setAmount(order.getTotalAmount());
        payment.setOrder(order);

        payment.setCreatedDate(LocalDateTime.now());
        payment.setIsActive(true);

        return PaymentResponseDTO.fromEntity(paymentRepository.save(payment));
    }

    //find payment
    public Payment findPayment(Integer id) {
        return paymentRepository.findByActiveId(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment not found"));
    }

    //Refund Payment
    public PaymentResponseDTO refundPayment(Integer paymentId) {
        Payment payment = findPayment(paymentId);

        payment.setStatus("REFUNDED");

        payment.setUpdatedDate(LocalDateTime.now());

        return PaymentResponseDTO.fromEntity(paymentRepository.save(payment));
    }

    //Complete Payment
    public PaymentResponseDTO completePayment(Integer paymentId) {
        Payment payment = findPayment(paymentId);

        payment.setStatus("COMPLETED");
        payment.setProcessedAt(LocalDateTime.now());
        payment.setUpdatedDate(LocalDateTime.now());

        return PaymentResponseDTO.fromEntity(paymentRepository.save(payment));
    }

    //Get Payment By Order
    public PaymentResponseDTO getPaymentByOrder(Integer orderId) {

        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        return PaymentResponseDTO.fromEntity(payment);
    }

    //get payments
    public List<PaymentSummaryDTO> getPayments(
            String method,
            String status,
            LocalDateTime from,
            LocalDateTime to,
            Pageable pageable) {
        return paymentRepository.filterPayments(method, status, from, to, pageable)
                .map(PaymentSummaryDTO::fromEntity).toList();
    }

    //Analytics by payment method
    public List<PaymentAnalyticsDTO> getAnalyticsByMethod() {
        return paymentRepository.getTotalByMethod().stream()
                .map(obj -> new PaymentAnalyticsDTO(
                        (String) obj[0],
                        ((Number) obj[1]).doubleValue()
                )).toList();
    }
}
