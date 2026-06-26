package com.example.Food.Delivery.Platform.Controllers;

import com.example.Food.Delivery.Platform.DTO.response.CustomerResponseDTO;
import com.example.Food.Delivery.Platform.DTO.response.report.DailySummaryDTO;
import com.example.Food.Delivery.Platform.DTO.response.report.DriverLeaderboardDTO;
import com.example.Food.Delivery.Platform.DTO.response.report.OrderCountDTO;
import com.example.Food.Delivery.Platform.DTO.response.report.RevenueReportDTO;
import com.example.Food.Delivery.Platform.Services.ReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportingController {
    private final ReportingService reportingService;

    @Autowired

    public ReportingController(ReportingService reportingService) {
        this.reportingService = reportingService;
    }

    //Revenue for restaurant
    @GetMapping("/revenue/restaurant/{restaurantId}")
    public ResponseEntity<RevenueReportDTO> revenue(
            @PathVariable Integer restaurantId,
            @RequestParam LocalDate date) {

        return ResponseEntity.ok(reportingService.getRestaurantRevenue(restaurantId, date));
    }

    //Total orders
    @GetMapping("/orders/count/restaurant/{restaurantId}")
    public ResponseEntity<OrderCountDTO> orderCount(
            @PathVariable Integer restaurantId) {

        return ResponseEntity.ok(reportingService.getRestaurantOrderCount(restaurantId));
    }

    //Top 10 customers
    @GetMapping("/customers/top-loyalty")
    public ResponseEntity<List<CustomerResponseDTO>> topCustomers() {

        return ResponseEntity.ok(reportingService.getTopLoyalCustomers());
    }

    //Drivers leaderboard
    @GetMapping("/drivers/leaderboard")
    public ResponseEntity<List<DriverLeaderboardDTO>> leaderboard() {

        return ResponseEntity.ok(reportingService.getDriversLeaderboard());
    }

    //Daily summary
    @GetMapping("/platform/daily-summary")
    public ResponseEntity<DailySummaryDTO> summary(@RequestParam LocalDate date) {

        return ResponseEntity.ok(reportingService.getDailySummary(date));
    }
}
