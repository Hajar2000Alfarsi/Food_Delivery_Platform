package com.example.Food.Delivery.Platform.Services;

import com.example.Food.Delivery.Platform.DTO.request.CustomerAddressRequestDTO;
import com.example.Food.Delivery.Platform.DTO.request.CustomerRequestDTO;
import com.example.Food.Delivery.Platform.DTO.response.CustomerResponseDTO;
import com.example.Food.Delivery.Platform.Entities.Customer;
import com.example.Food.Delivery.Platform.Entities.CustomerAddress;
import com.example.Food.Delivery.Platform.Exceptions.DuplicateResourceException;
import com.example.Food.Delivery.Platform.Repositories.CustomerAddressRepository;
import com.example.Food.Delivery.Platform.Repositories.CustomerRepository;
import com.example.Food.Delivery.Platform.Utils.HelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.example.Food.Delivery.Platform.Utils.HelperUtils.findActiveCustomer;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerAddressRepository customerAddressRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerAddressRepository customerAddressRepository) {
        this.customerRepository = customerRepository;
        this.customerAddressRepository = customerAddressRepository;
    }

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

    //delete
    public void deactivateCustomer(Integer customerId) {

        Customer customer = findActiveCustomer(customerId);

        customer.setIsActive(false);

        customer.setUpdatedDate(LocalDateTime.now());

        customerRepository.save(customer);
    }
}
