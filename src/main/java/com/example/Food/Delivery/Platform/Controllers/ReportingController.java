package com.example.Food.Delivery.Platform.Controllers;

import com.example.Food.Delivery.Platform.DTO.response.report.RevenueReportDTO;
import com.example.Food.Delivery.Platform.Services.ReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reports")
public class ReportingController {
    private final ReportingService reportingService;

    @Autowired

    public ReportingController(ReportingService reportingService) {
        this.reportingService = reportingService;
    }

    @GetMapping("/revenue/restaurant/{restaurantId}")
    public ResponseEntity<RevenueReportDTO> revenue(
            @PathVariable Integer restaurantId,
            @RequestParam LocalDate date) {

        return ResponseEntity.ok(reportingService.getRestaurantRevenue(restaurantId, date));
    }
}
