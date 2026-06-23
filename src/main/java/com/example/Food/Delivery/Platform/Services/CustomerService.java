package com.example.Food.Delivery.Platform.Services;

import com.example.Food.Delivery.Platform.DTO.request.CustomerAddressRequestDTO;
import com.example.Food.Delivery.Platform.DTO.request.CustomerRequestDTO;
import com.example.Food.Delivery.Platform.DTO.response.CustomerResponseDTO;
import com.example.Food.Delivery.Platform.Entities.Customer;
import com.example.Food.Delivery.Platform.Entities.CustomerAddress;
import com.example.Food.Delivery.Platform.Exceptions.ResourceNotFoundException;
import com.example.Food.Delivery.Platform.Repositories.CustomerAddressRepository;
import com.example.Food.Delivery.Platform.Repositories.CustomerRepository;
import com.example.Food.Delivery.Platform.Utils.HelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
       Customer customer = new Customer();

        customer.setCreatedDate(LocalDateTime.now());
        customer.setUpdatedDate(LocalDateTime.now());
        customer.setIsActive(true);

        return CustomerResponseDTO.fromEntity(customerRepository.save(customer));
    }

    public CustomerResponseDTO createCustomer(CustomerRequestDTO dto, CustomerAddressRequestDTO initialAddress) {
        String customerCode = HelperUtils.generateCode("CUST");

        Customer customer = dto.toEntity(dto.getPasswordHash(), customerCode);

        customer.setIsActive(true);
        customer.setCreatedDate(LocalDateTime.now());
        customer.setUpdatedDate(LocalDateTime.now());

        CustomerAddress address = initialAddress.toEntity();
        address.setCustomer(customer);

        customer.setAddresses(List.of(address));

        Customer savedCustomer = customerRepository.save(customer);

        return CustomerResponseDTO.fromEntity(savedCustomer);
    }

}
