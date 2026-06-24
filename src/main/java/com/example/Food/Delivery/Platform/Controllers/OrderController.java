package com.example.Food.Delivery.Platform.Controllers;

import com.example.Food.Delivery.Platform.DTO.request.CorporateOrderRequestDTO;
import com.example.Food.Delivery.Platform.DTO.request.OrderItemRequestDTO;
import com.example.Food.Delivery.Platform.DTO.response.CorporateOrderResponseDTO;
import com.example.Food.Delivery.Platform.DTO.response.FoodOrderResponseDTO;
import com.example.Food.Delivery.Platform.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    //create order without note
    @PostMapping("/{customerId}/restaurant/{restaurantId}")
    public ResponseEntity<FoodOrderResponseDTO> createOrder(
            @PathVariable Integer customerId,
            @PathVariable Integer restaurantId,
            @RequestBody List<OrderItemRequestDTO> items) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(customerId, restaurantId, items));
    }

    //create order with note
    @PostMapping("/{customerId}/restaurant/{restaurantId}/note/{note}")
    public ResponseEntity<FoodOrderResponseDTO> createOrder(
            @PathVariable Integer customerId,
            @PathVariable Integer restaurantId,
            @RequestBody List<OrderItemRequestDTO> items,
            @RequestParam String note) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(customerId, restaurantId, items, note));
    }

    //Add item
    @PostMapping("{id}/items")
    public ResponseEntity<FoodOrderResponseDTO> addItems(@PathVariable Integer orderID,
                                                         @RequestBody OrderItemRequestDTO dto){
        return ResponseEntity.ok(orderService.addMenuItemToOrder(orderID,dto.getMenuItemId(),dto.getQuantity()));
    }

    //Remove Item(soft Delete)
    @DeleteMapping("/{id}/items/{itemId}")
    public ResponseEntity<String> removeItem(
            @PathVariable Integer orderID,
            @PathVariable Integer OrderItemId) {
        orderService.removeMenuItemFromOrder(orderID, OrderItemId);
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
    public ResponseEntity<FoodOrderResponseDTO> confirmOrder(@PathVariable Integer orderId) {
        return ResponseEntity.ok(orderService.confirmOrder(orderId));
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
    public ResponseEntity<String> cancelOrder(@PathVariable Integer orderID){
        orderService.cancelOrder(orderID);
        return ResponseEntity.ok("Order Canceled Successfully");
    }

    //get full order details(with items)
    @PutMapping("/{id}")
    public ResponseEntity<FoodOrderResponseDTO> getOrder(@PathVariable Integer orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    //get by active Restaurant
    @PutMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<FoodOrderResponseDTO>> getByRestaurant(@PathVariable Integer restaurantId,
                                                                @RequestParam String status) {
        return ResponseEntity.ok(orderService.getOrdersByRestaurant(restaurantId, status));
    }

    //Create a corporate order
    @PutMapping("/corporate")
    ResponseEntity<CorporateOrderResponseDTO> corporate(@RequestBody CorporateOrderRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.placeCorporateOrder(dto));
    }

}
