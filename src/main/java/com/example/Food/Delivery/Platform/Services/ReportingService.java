package com.example.Food.Delivery.Platform.Services;

import com.example.Food.Delivery.Platform.DTO.response.CancellationRateDTO;
import com.example.Food.Delivery.Platform.DTO.response.CustomerResponseDTO;
import com.example.Food.Delivery.Platform.DTO.response.DriverEarningsDTO;
import com.example.Food.Delivery.Platform.DTO.response.report.DailySummaryDTO;
import com.example.Food.Delivery.Platform.DTO.response.report.DriverLeaderboardDTO;
import com.example.Food.Delivery.Platform.DTO.response.report.OrderCountDTO;
import com.example.Food.Delivery.Platform.DTO.response.report.RevenueReportDTO;
import com.example.Food.Delivery.Platform.Entities.Delivery;
import com.example.Food.Delivery.Platform.Entities.DeliveryDriver;
import com.example.Food.Delivery.Platform.Entities.FoodOrder;
import com.example.Food.Delivery.Platform.Repositories.CustomerRepository;
import com.example.Food.Delivery.Platform.Repositories.DeliveryRepository;
import com.example.Food.Delivery.Platform.Repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public RevenueReportDTO getRestaurantRevenue(
            Integer restaurantId,
            LocalDate date,
            LocalDateTime from,
            LocalDateTime to) {
        double revenue = 0.0;

        //case 1: daily report
        if (date != null){
                                                            //start             , end
            revenue = orderRepository.findByOrderDateBetween(date.atStartOfDay(), date.plusDays(1).atStartOfDay())
                    .stream()
                    .filter(o -> o.getRestaurant() != null
                            && o.getRestaurant().getId().equals(restaurantId))
                    .map(FoodOrder::getTotalAmount)
                    .filter(Objects::nonNull)
                    .mapToDouble(Double::doubleValue)
                    .sum();
        }
        //case 2: date range report
        else if (from != null && to != null) {
            revenue = orderRepository.getRevenueBetweenDates(restaurantId, from, to);
        }
        //case 3: no filter -> total revenue
        else {

            revenue = orderRepository.getTotalRevenue(restaurantId);
        }
        return new RevenueReportDTO(restaurantId, revenue);
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

    //Restaurant Revenue
    /*public RevenueReportDTO getRestaurantRevenue(
            Integer restaurantId,
            LocalDateTime from,
            LocalDateTime to) {
        Double revenue = orderRepository.getRevenueBetweenDates(restaurantId, from, to);

        return new RevenueReportDTO(restaurantId, revenue);
    }*/

    //Driver Earnings Report
    public DriverEarningsDTO getDriverEarnings(Integer driverId, LocalDateTime from, LocalDateTime to) {
        List<Delivery> deliveries = deliveryRepository.findDeliveredBetween(driverId, from, to);

        long count = deliveries.size();

        double earnings = deliveries.stream()
                .mapToDouble(d -> d.getOrder().getDeliveryFee())
                .sum();

        return new DriverEarningsDTO(driverId, count, earnings);
    }

    //Cancellation Rate Report
    public CancellationRateDTO getCancellationRate(LocalDateTime from, LocalDateTime to) {

        long completed = orderRepository.countCompleted(from, to);
        long cancelled = orderRepository.countCancelled(from, to);

        double rate = 0;

        if (completed + cancelled > 0) {
            rate = (cancelled * 100.0) / (completed + cancelled);
        }

        return new CancellationRateDTO(completed, cancelled, rate);
    }
}
