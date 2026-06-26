package com.example.Food.Delivery.Platform.Services;

import com.example.Food.Delivery.Platform.DTO.response.report.OrderCountDTO;
import com.example.Food.Delivery.Platform.DTO.response.report.RevenueReportDTO;
import com.example.Food.Delivery.Platform.Entities.FoodOrder;
import com.example.Food.Delivery.Platform.Repositories.CustomerRepository;
import com.example.Food.Delivery.Platform.Repositories.DeliveryRepository;
import com.example.Food.Delivery.Platform.Repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReportingService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final DeliveryRepository deliveryRepository;

    @Autowired

    public ReportingService(OrderRepository orderRepository, CustomerRepository customerRepository, DeliveryRepository deliveryRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.deliveryRepository = deliveryRepository;
    }

    //Revenue for restaurant
    public RevenueReportDTO getRestaurantRevenue(Integer restaurantId, LocalDate date) {
                                                                //start             , end
        double revenue = orderRepository.findByOrderDateBetween(date.atStartOfDay(), date.plusDays(1).atStartOfDay())
                .stream()
                .filter(o -> o.getRestaurant().getId().equals(restaurantId))
                .mapToDouble(FoodOrder::getTotalAmount)
                .sum();

        return new RevenueReportDTO(restaurantId, date.toString(), revenue);
    }


}
