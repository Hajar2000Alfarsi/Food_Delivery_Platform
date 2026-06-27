package com.example.Food.Delivery.Platform.Services;

import com.example.Food.Delivery.Platform.DTO.request.CorporateOrderRequestDTO;
import com.example.Food.Delivery.Platform.DTO.request.OrderItemRequestDTO;
import com.example.Food.Delivery.Platform.DTO.response.CorporateOrderResponseDTO;
import com.example.Food.Delivery.Platform.DTO.response.FoodOrderResponseDTO;
import com.example.Food.Delivery.Platform.DTO.response.OrderItemResponseDTO;
import com.example.Food.Delivery.Platform.Entities.*;
import com.example.Food.Delivery.Platform.Exceptions.InvalidOrderStateException;
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
    private CorporateOrderRepository corporateOrderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, RestaurantRepository restaurantRepository, MenuItemRepository menuItemRepository, OrderItemRepository orderItemRepository, CorporateOrderRepository corporateOrderRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
        this.orderItemRepository = orderItemRepository;
        this.corporateOrderRepository = corporateOrderRepository;
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

        //create order items list
        List<OrderItem> orderItems = new ArrayList<>();
        double subtotal = 0.0;

        for (OrderItemRequestDTO dto : items){
            MenuItem menuItem = menuItemRepository.findByActiveId(dto.getMenuItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));

            OrderItem item = new OrderItem();
            item.setMenuItem(menuItem);
            item.setQuantity(dto.getQuantity());
            item.setUnitPrice(menuItem.getPrice());
            double totalPrice  = menuItem.getPrice() * dto.getQuantity();
            item.setTotalPrice(totalPrice);
            item.setOrder(foodOrder);

            orderItems.add(item);

            subtotal += totalPrice;
        }

        foodOrder.setOrderItems(orderItems);


        foodOrder.setSubtotal(subtotal);
        foodOrder.setDeliveryFee(restaurant.getDeliveryFee() != null ? restaurant.getDeliveryFee() : 0.0);
        foodOrder.setDiscountAmount(0.0);
        foodOrder.setTotalAmount(subtotal + (restaurant.getDeliveryFee() != null ? restaurant.getDeliveryFee() : 0.0));

        return FoodOrderResponseDTO.fromEntity(orderRepository.save(foodOrder));
    }

    //find order
    private FoodOrder findOrder(Integer id) {
        return orderRepository.findByActiveId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }


    //ADD ITEM
    public FoodOrderResponseDTO addMenuItemToOrder(Integer orderId,
                                                   Integer menuItemId,
                                                   int quantity) {

        FoodOrder order = findOrder(orderId);

        if (!order.getStatus().equals("PENDING")) {
            throw new InvalidOrderStateException("Cannot modify order");
        }

        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));

        OrderItem item = new OrderItem();
        item.setMenuItem(menuItem);
        item.setQuantity(quantity);
        item.setUnitPrice(menuItem.getPrice());
        double totalPrice = menuItem.getPrice() * quantity;
        item.setTotalPrice(totalPrice);

        item.setOrder(order);
        //ensure list exists
        if (order.getOrderItems() == null) {
            order.setOrderItems(new ArrayList<>());
        }
        order.getOrderItems().add(item);

        //Calculate subtotal
        double subtotal = order.getOrderItems().stream()
                .mapToDouble(OrderItem::getTotalPrice).sum();
        order.setSubtotal(subtotal);

        //Calculate total
        double deliveryFee = order.getDeliveryFee() != null ? order.getDeliveryFee() : 0.0;
        double discount = order.getDiscountAmount() != null ? order.getDiscountAmount() : 0.0;

        totalPrice = subtotal + deliveryFee - discount;
        order.setTotalAmount(totalPrice);

        order.setUpdatedDate(LocalDateTime.now());

        return FoodOrderResponseDTO.fromEntity(orderRepository.save(order));
    }

    //REMOVE ITEM (soft delete)
    public void removeMenuItemFromOrder(Integer orderId,
                                        Integer orderItemId) {

        FoodOrder order = findOrder(orderId);
        if (order == null) {
            throw new ResourceNotFoundException("Order not found");
        }

        OrderItem item = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        item.setIsActive(false);
        orderItemRepository.save(item);
    }

    //Apply discount
    public FoodOrderResponseDTO applyDiscount(Integer orderId, double discount){
        FoodOrder order = findOrder(orderId);

        if (order == null) {
            throw new ResourceNotFoundException("Order not found");
        }

        order.setDiscountAmount(discount);

        return FoodOrderResponseDTO.fromEntity(orderRepository.save(order));
    }


    //Update status
    public FoodOrderResponseDTO updateOrderStatus(Integer orderId, String newStatus){
        FoodOrder order = findOrder(orderId);

        if (order == null) {
            throw new ResourceNotFoundException("Order not found");
        }

        order.setStatus(newStatus);

        return FoodOrderResponseDTO.fromEntity(orderRepository.save(order));
    }

    //CancelOrder
    public void cancelOrder(Integer orderId) {
        FoodOrder order = findOrder(orderId);

        if (!order.getStatus().equals("PENDING")) {
            throw new InvalidOrderStateException("Only PENDING orders can be cancelled");
        }

        order.setStatus("CANCELLED");

        orderRepository.save(order);
    }

    //CALCULATE TOTALS
    public FoodOrderResponseDTO calculateOrderTotals(Integer orderId){
        FoodOrder order = findOrder(orderId);

        if (order == null) {
            throw new ResourceNotFoundException("Order not found");
        }

        double subtotal = order.getOrderItems().stream().mapToDouble(OrderItem::getTotalPrice).sum();

        double total = subtotal + order.getDeliveryFee() - order.getDiscountAmount();

        order.setSubtotal(subtotal);
        order.setTotalAmount(total);

        return FoodOrderResponseDTO.fromEntity(orderRepository.save(order));
    }

    //Corporate Order(add corporate order)
    public CorporateOrderResponseDTO placeCorporateOrder(CorporateOrderRequestDTO dto) {
        Restaurant restaurant = restaurantRepository.findByActiveId(dto.getRestaurantId())
                .orElseThrow(()->new ResourceNotFoundException("Restaurant not found"));

        CorporateOrder order = dto.toEntity();

        order.setCorporateCode(HelperUtils.generateCode("CROP"));

        order.setRestaurant(restaurant);
        order.setStatus("PENDING");
        order.setCreatedDate(LocalDateTime.now());
        order.setIsActive(true);

        return CorporateOrderResponseDTO.fromEntity(corporateOrderRepository.save(order));
    }

    //Get Order By ID
    public FoodOrderResponseDTO getOrderById(Integer orderId) {
        return FoodOrderResponseDTO.fromEntity(findOrder(orderId));
    }

    //restaurant order
    public List<FoodOrderResponseDTO> getOrdersByRestaurant(Integer restaurantId, String status) {
        return orderRepository.findByRestaurantIdAndStatus(restaurantId, status)
                .stream().map(FoodOrderResponseDTO::fromEntity).toList();
    }

    //confirm order
    public FoodOrderResponseDTO confirmOrder(Integer orderId) {
        FoodOrder order = findOrder(orderId);

         if (!order.getStatus().equals("PENDING")) {
            throw new InvalidOrderStateException("Only PENDING orders can be confirmed");
        }

         order.setStatus("CONFIRMED");
         order.setUpdatedDate(LocalDateTime.now());

         return FoodOrderResponseDTO.fromEntity(orderRepository.save(order));
    }

    //reorder
    public FoodOrderResponseDTO reorder(Integer orderId){
        FoodOrder oldOrder = findOrder(orderId);
        FoodOrder newOrder = new FoodOrder();

        newOrder.setOrderCode(HelperUtils.generateCode("ORD"));

        newOrder.setCustomer(oldOrder.getCustomer());
        newOrder.setRestaurant(oldOrder.getRestaurant());

        newOrder.setOrderDate(LocalDateTime.now());

        newOrder.setStatus("PENDING");

        newOrder.setSubtotal(oldOrder.getSubtotal());

        newOrder.setDeliveryFee(oldOrder.getDeliveryFee());

        newOrder.setDiscountAmount(0.0);

        newOrder.setTotalAmount(oldOrder.getSubtotal() + oldOrder.getDeliveryFee());

        newOrder.setIsActive(true);

        List<OrderItem> items = new ArrayList<>();

        for(OrderItem oldItem : oldOrder.getOrderItems()){

            OrderItem item = new OrderItem();

            item.setMenuItem(oldItem.getMenuItem());

            item.setQuantity(oldItem.getQuantity());

            item.setUnitPrice(oldItem.getUnitPrice());

            item.setTotalPrice(oldItem.getTotalPrice());

            item.setOrder(newOrder);

            items.add(item);
        }

        newOrder.setOrderItems(items);

        return FoodOrderResponseDTO.fromEntity(orderRepository.save(newOrder));
    }
}
