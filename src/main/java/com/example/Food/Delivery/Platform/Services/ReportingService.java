package com.example.Food.Delivery.Platform.Services;

import com.example.Food.Delivery.Platform.DTO.response.CustomerResponseDTO;
import com.example.Food.Delivery.Platform.DTO.response.report.DailySummaryDTO;
import com.example.Food.Delivery.Platform.DTO.response.report.DriverLeaderboardDTO;
import com.example.Food.Delivery.Platform.DTO.response.report.OrderCountDTO;
import com.example.Food.Delivery.Platform.DTO.response.report.RevenueReportDTO;
import com.example.Food.Delivery.Platform.Entities.DeliveryDriver;
import com.example.Food.Delivery.Platform.Entities.FoodOrder;
import com.example.Food.Delivery.Platform.Repositories.CustomerRepository;
import com.example.Food.Delivery.Platform.Repositories.DeliveryRepository;
import com.example.Food.Delivery.Platform.Repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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
                .filter(o -> o.getRestaurant() != null
                        && o.getRestaurant().getId().equals(restaurantId))
                .map(FoodOrder::getTotalAmount)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .sum();

        return new RevenueReportDTO(restaurantId, date.toString(), revenue);
    }

    //Total orders
    public OrderCountDTO getRestaurantOrderCount(Integer restaurantId) {

        Long count = orderRepository.countCompletedOrders(restaurantId);

        return new OrderCountDTO(restaurantId, count);
    }

    //Top 10 customers
    public List<CustomerResponseDTO> getTopLoyalCustomers() {

        return customerRepository.findTopCustomersByLoyalty(
                        PageRequest.of(0,10))
                .stream()
                .map(CustomerResponseDTO::fromEntity)
                .toList();
    }

    // Drivers leaderboard
    public List<DriverLeaderboardDTO> getDriversLeaderboard() {

        return deliveryRepository.getDriverLeaderboard()
                .stream()
                .map(obj ->
                        new DriverLeaderboardDTO(
                                ((DeliveryDriver) obj[0]).getId(),
                                ((DeliveryDriver) obj[0]).getFirstName() + " " +
                                        ((DeliveryDriver) obj[0]).getLastName(),
                                (Long) obj[1]))
                .toList();
    }

    // Daily summary
    public DailySummaryDTO getDailySummary(LocalDate date) {

        List<FoodOrder> orders = orderRepository.findByOrderDateBetween(date.atStartOfDay(), date.plusDays(1).atStartOfDay());

        //total number of orders
        long totalOrders = orders.size();

        double fees = orders.stream()
                .mapToDouble(o -> o.getDeliveryFee() == null ? 0.0 : o.getDeliveryFee())
                .sum();

        return new DailySummaryDTO(date.toString(), totalOrders, fees);
    }

}
