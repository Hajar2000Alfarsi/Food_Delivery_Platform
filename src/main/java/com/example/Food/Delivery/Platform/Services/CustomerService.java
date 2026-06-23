package com.example.Food.Delivery.Platform.Services;

import com.example.Food.Delivery.Platform.DTO.request.CustomerAddressRequestDTO;
import com.example.Food.Delivery.Platform.DTO.request.CustomerRequestDTO;
import com.example.Food.Delivery.Platform.DTO.response.CustomerAddressResponseDTO;
import com.example.Food.Delivery.Platform.DTO.response.CustomerResponseDTO;
import com.example.Food.Delivery.Platform.DTO.response.FoodOrderResponseDTO;
import com.example.Food.Delivery.Platform.Entities.Customer;
import com.example.Food.Delivery.Platform.Entities.CustomerAddress;
import com.example.Food.Delivery.Platform.Exceptions.DuplicateResourceException;
import com.example.Food.Delivery.Platform.Exceptions.ResourceNotFoundException;
import com.example.Food.Delivery.Platform.Repositories.CustomerAddressRepository;
import com.example.Food.Delivery.Platform.Repositories.CustomerRepository;
import com.example.Food.Delivery.Platform.Repositories.OrderRepository;
import com.example.Food.Delivery.Platform.Utils.HelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerAddressRepository customerAddressRepository;
    private final OrderRepository orderRepository;

    public CustomerService(CustomerRepository customerRepository, CustomerAddressRepository customerAddressRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.customerAddressRepository = customerAddressRepository;
        this.orderRepository = orderRepository;
    }

    @Autowired


    //create customer
    public CustomerResponseDTO createCustomer(CustomerRequestDTO dto) {
        if (customerRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email already exists");
        }
        String customerCode = HelperUtils.generateCode("CUST");

        Customer customer = dto.toEntity(customerCode);

        customer.setCreatedDate(LocalDateTime.now());
        customer.setUpdatedDate(LocalDateTime.now());
        customer.setIsActive(true);

        Customer saved = customerRepository.save(customer);

        return CustomerResponseDTO.fromEntity(saved);
    }

    public CustomerResponseDTO createCustomer(CustomerRequestDTO dto, CustomerAddressRequestDTO initialAddress) {
        CustomerResponseDTO response = createCustomer(dto);

            Customer customer = findActiveCustomer(response.getId());

        CustomerAddress address =
                initialAddress.toEntity();

        address.setCustomer(customer);

        address.setCreatedDate(LocalDateTime.now());
        address.setUpdatedDate(LocalDateTime.now());
        address.setIsActive(true);

        customerAddressRepository.save(address);

        return CustomerResponseDTO.fromEntity(customer);
    }

    private Customer findActiveCustomer(Integer id) {
        return customerRepository.getByActiveId(id).orElseThrow(() ->
                new ResourceNotFoundException("Customer not found with id: " + id));

    }

    public CustomerResponseDTO addAddress(Integer customerId, CustomerAddressRequestDTO addressDTO) {
        Customer customer = findActiveCustomer(customerId);

        CustomerAddress address = addressDTO.toEntity();

        address.setCustomer(customer);

        address.setCreatedDate(LocalDateTime.now());
        address.setUpdatedDate(LocalDateTime.now());
        address.setIsActive(true);

        customerAddressRepository.save(address);

        return CustomerResponseDTO.fromEntity(customer);
    }

    public CustomerResponseDTO updateLoyaltyPoints(Integer customerId, int points) {
        Customer customer = findActiveCustomer(customerId);

        customer.setLoyaltyPoints(customer.getLoyaltyPoints() + points);

        customer.setUpdatedDate(LocalDateTime.now());

        return CustomerResponseDTO.fromEntity(customerRepository.save(customer));
    }

    public CustomerResponseDTO applyLoyaltyPenalty(Integer customerId, int pointsDeducted) {

        Customer customer = findActiveCustomer(customerId);

        customer.setLoyaltyPoints(customer.getLoyaltyPoints() - pointsDeducted);

        customer.setUpdatedDate(LocalDateTime.now());

        return CustomerResponseDTO.fromEntity(customerRepository.save(customer));
    }

    //get all
    public List<CustomerResponseDTO> getAllCustomers() {

        return customerRepository.findAllActive()
                .stream()
                .map(CustomerResponseDTO::fromEntity)
                .toList();
    }

    //get customer by ID
    public CustomerResponseDTO getCustomerById(Integer id) {

        return CustomerResponseDTO.fromEntity(findActiveCustomer(id));

    }

    //get customer by email
    public CustomerResponseDTO getCustomerByEmail(String email) {

        Customer customer = customerRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Customer not found"));

        return CustomerResponseDTO.fromEntity(customer);
    }

    //get customer address
    public List<CustomerAddressResponseDTO> getCustomerAddresses(Integer customerId) {
        findActiveCustomer(customerId);

        return customerAddressRepository.findByCustomerId(customerId)
                .stream()
                .map(CustomerAddressResponseDTO::fromEntity)
                .toList();
    }

    //default address
    public void setDefaultAddress(Integer addressId) {
        CustomerAddress address = customerAddressRepository.findByActiveId(addressId).orElseThrow(() ->
                new ResourceNotFoundException("Address Not Found"));

        //bring all address of customer(one customer came have many address)
        List<CustomerAddress> addresses = customerAddressRepository.findByCustomerId(address.getCustomer().getId());

        //make sure the previous address no one is default
        for(CustomerAddress a : addresses){
            a.setIsDefault(false);
            customerAddressRepository.save(a);
        }

        //set new address as default
        address.setIsDefault(true);
        customerAddressRepository.save(address);
    }

    //delete address
    public void deleteAddress(Integer addressId) {

        CustomerAddress address = customerAddressRepository.findByActiveId(addressId).orElseThrow(()->
                new ResourceNotFoundException("Address Not found"));

        address.setIsActive(false);
        address.setUpdatedDate(LocalDateTime.now());

        customerAddressRepository.save(address);
    }

    //delete customer
    public void deactivateCustomer(Integer customerId) {

        Customer customer = findActiveCustomer(customerId);

        customer.setIsActive(false);

        customer.setUpdatedDate(LocalDateTime.now());

        customerRepository.save(customer);
    }

    //get customer order
    public List<FoodOrderResponseDTO> getCustomerOrders(Integer customerId){

        findActiveCustomer(customerId);

        return orderRepository
                .findByCustomerId(customerId)
                .stream()
                .map(FoodOrderResponseDTO::fromEntity)
                .toList();
    }
}
