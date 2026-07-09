package com.example.Food.Delivery.Platform.Controllers;

import com.example.Food.Delivery.Platform.DTO.request.CorporateOrderRequestDTO;
import com.example.Food.Delivery.Platform.DTO.request.OrderItemRequestDTO;
import com.example.Food.Delivery.Platform.DTO.response.CorporateOrderResponseDTO;
import com.example.Food.Delivery.Platform.DTO.response.ETAResponseDTO;
import com.example.Food.Delivery.Platform.DTO.response.FoodOrderResponseDTO;
import com.example.Food.Delivery.Platform.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    //create order without note
    @PostMapping("/customer/{customerId}/restaurant/{restaurantId}")
    public ResponseEntity<FoodOrderResponseDTO> createOrder(
            @PathVariable Integer customerId,
            @PathVariable Integer restaurantId,
            @RequestBody List<OrderItemRequestDTO> items) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(customerId, restaurantId, items));
    }

    //create order with note
    @PostMapping("/customer/{customerId}/restaurant/{restaurantId}/note/{note}")
    public ResponseEntity<FoodOrderResponseDTO> createOrder(
            @PathVariable Integer customerId,
            @PathVariable Integer restaurantId,
            @RequestBody List<OrderItemRequestDTO> items,
            @PathVariable String note) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(customerId, restaurantId, items, note));
    }

    //Add item
    @PostMapping("{id}/items")
    public ResponseEntity<FoodOrderResponseDTO> addItems(@PathVariable Integer id,
                                                         @RequestBody OrderItemRequestDTO dto){
        return ResponseEntity.ok(orderService.addMenuItemToOrder(id,dto.getMenuItemId(),dto.getQuantity()));
    }

    //Remove Item(soft Delete)
    @DeleteMapping("/{id}/items/{itemId}")
    public ResponseEntity<String> removeItem(
            @PathVariable Integer id,
            @PathVariable Integer itemId) {
        orderService.removeMenuItemFromOrder(id, itemId);
        return ResponseEntity.ok("Successfully  deleted");
    }

    //Discount Order
    @PutMapping("{id}/discount/{amount}")
    public ResponseEntity<FoodOrderResponseDTO> discountOrder(
            @PathVariable Integer id,
            @PathVariable double amount) {
     return ResponseEntity.ok(orderService.applyDiscount(id, amount));
    }

    //confirm order
    @PutMapping("{id}/confirm")
    public ResponseEntity<FoodOrderResponseDTO> confirmOrder(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.confirmOrder(id));
    }

    //update order status
    @PutMapping("/{id}/status/{status}")
    public ResponseEntity<FoodOrderResponseDTO> updateStatus(
            @PathVariable Integer id,
            @PathVariable String status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }

    //cancel order
    @PutMapping("/{id}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable Integer id){
        orderService.cancelOrder(id);
        return ResponseEntity.ok("Order Canceled Successfully");
    }

    //get full order details(with items)
    @GetMapping("/{id}")
    public ResponseEntity<FoodOrderResponseDTO> getOrder(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    //get by active Restaurant
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<FoodOrderResponseDTO>> getByRestaurant(@PathVariable Integer restaurantId,
                                                                @RequestParam String status) {
        return ResponseEntity.ok(orderService.getOrdersByRestaurant(restaurantId, status));
    }

    //Create a corporate order
    @PostMapping("/corporate")
    ResponseEntity<CorporateOrderResponseDTO> corporate(@RequestBody CorporateOrderRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeCorporateOrder(dto));
    }

    //reorder
    @PostMapping("/{id}/reorder")
    public ResponseEntity<FoodOrderResponseDTO> reorder(@PathVariable Integer id){

        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.reorder(id));
    }

    //filter order list
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Page<FoodOrderResponseDTO>> getCustomerOrders(
            @PathVariable Integer customerId,
            @RequestParam String status,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){

        return ResponseEntity.ok(orderService.getCustomerOrdersFiltered(
                        customerId, status, from, to, page, size));
    }

    //Estimated delivery time based on driver location and distance
    @GetMapping("/{id}/eta")
    public ResponseEntity<ETAResponseDTO> getETA(@PathVariable Integer id){

        return ResponseEntity.ok(orderService.getEstimatedDeliveryTime(id));
    }

    //timeline
    @GetMapping("/{id}/timeline")
    public ResponseEntity<List<String>> getOrderTimeline(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.getOrderTimeline(id));
    }
}
