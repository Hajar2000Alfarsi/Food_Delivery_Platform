package com.example.Food.Delivery.Platform.Controllers;

import com.example.Food.Delivery.Platform.DTO.request.CustomerAddressRequestDTO;
import com.example.Food.Delivery.Platform.DTO.request.CustomerRequestDTO;
import com.example.Food.Delivery.Platform.DTO.response.CustomerAddressResponseDTO;
import com.example.Food.Delivery.Platform.DTO.response.CustomerResponseDTO;
import com.example.Food.Delivery.Platform.DTO.response.FoodOrderResponseDTO;
import com.example.Food.Delivery.Platform.Services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //create customer
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@Valid @RequestBody CustomerRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(dto));
    }

    //get all customer
    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers(){

        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    //get customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO>
    getCustomerById(@PathVariable Integer id){

        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    //get customer by email
    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerResponseDTO> getCustomerByEmail(@PathVariable String email){

        return ResponseEntity.ok(customerService.getCustomerByEmail(email));
    }

    //soft delete for customer
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateCustomer(@PathVariable Integer id){

        customerService.deactivateCustomer(id);


        return ResponseEntity.ok("Successfully  deleted");
    }

    //add loyalty points
    @PutMapping("/{id}/loyalty/add/{points}")
    public ResponseEntity<CustomerResponseDTO> addPoints(@PathVariable Integer id, @PathVariable int points){

        return ResponseEntity.ok(customerService.updateLoyaltyPoints(id, points));
    }

    //detect loyalty points
    @PutMapping("/{id}/loyalty/deduct/{points}")
    public ResponseEntity<CustomerResponseDTO> deductPoints(@PathVariable Integer id, @PathVariable int points){

        return ResponseEntity.ok(customerService.applyLoyaltyPenalty(id, points));
    }

    //add customer address
    @PostMapping("/{id}/addresses")
    public ResponseEntity<CustomerAddressResponseDTO> addAddress(@PathVariable Integer id, @Valid @RequestBody CustomerAddressRequestDTO dto){

        return ResponseEntity.ok(customerService.addAddress(id, dto));
    }

    //get all address for one customer
    @GetMapping("/{id}/addresses")
    public ResponseEntity<List<CustomerAddressResponseDTO>> getAddresses(@PathVariable Integer id){

        return ResponseEntity.ok(customerService.getCustomerAddresses(id));
    }

    //set an address as default
    @PutMapping("/addresses/{addressId}/default")
    public ResponseEntity<String> setDefaultAddress(@PathVariable Integer addressId){

        customerService.setDefaultAddress(addressId);

        return ResponseEntity.ok("Address set successfully");
    }

    //delete address
    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Integer addressId){

        customerService.deleteAddress(addressId);

        return ResponseEntity.ok("Address Deleted Successfully");
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<FoodOrderResponseDTO>> getOrders(@PathVariable Integer id){

        return ResponseEntity.ok(customerService.getCustomerOrders(id));
    }

}
