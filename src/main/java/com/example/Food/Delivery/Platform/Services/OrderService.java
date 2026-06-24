package com.example.Food.Delivery.Platform.Services;

import com.example.Food.Delivery.Platform.DTO.request.OrderItemRequestDTO;
import com.example.Food.Delivery.Platform.DTO.response.FoodOrderResponseDTO;
import com.example.Food.Delivery.Platform.DTO.response.OrderItemResponseDTO;
import com.example.Food.Delivery.Platform.Entities.*;
import com.example.Food.Delivery.Platform.Exceptions.ResourceNotFoundException;
import com.example.Food.Delivery.Platform.Repositories.*;
import com.example.Food.Delivery.Platform.Utils.HelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, RestaurantRepository restaurantRepository, MenuItemRepository menuItemRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
        this.orderItemRepository = orderItemRepository;
    }

    //create Order if there is no notes
    public FoodOrderResponseDTO createOrder(Integer customerId,
                                            Integer restaurantId,
                                            List<OrderItemRequestDTO> items){
        return createOrder(customerId, restaurantId, items, null);
    }

    //Create Order if there is note
    public FoodOrderResponseDTO createOrder(Integer customerId,
                                            Integer restaurantId,
                                            List<OrderItemRequestDTO> items,
                                            String notes) {
        Customer customer = customerRepository.findByActiveId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        Restaurant restaurant = restaurantRepository.findByActiveId(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

        FoodOrder foodOrder = new FoodOrder();

        foodOrder.setOrderCode(HelperUtils.generateCode("ORD"));
        foodOrder.setOrderDate(LocalDateTime.now());
        foodOrder.setStatus("PENDING");
        foodOrder.setDeliveryNotes(notes);
        foodOrder.setIsActive(true);

        foodOrder.setCustomer(customer);
        foodOrder.setRestaurant(restaurant);

        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequestDTO dto : items){
            MenuItem menuItem = menuItemRepository.findByActiveId(dto.getMenuItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));

            OrderItem item = new OrderItem();
            item.setMenuItem(menuItem);
            item.setQuantity(dto.getQuantity());
            item.setUnitPrice(menuItem.getPrice());
            item.setTotalPrice(menuItem.getPrice() * dto.getQuantity());
            item.setOrder(foodOrder);

            orderItems.add(item);
        }

        foodOrder.setOrderItems(orderItems);
        return FoodOrderResponseDTO.fromEntity(orderRepository.save(foodOrder));
    }


}
