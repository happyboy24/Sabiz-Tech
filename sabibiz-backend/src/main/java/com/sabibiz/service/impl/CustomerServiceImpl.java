package com.sabibiz.service.impl;

import com.sabibiz.dto.request.CustomerRequest;
import com.sabibiz.dto.response.CustomerResponse;
import com.sabibiz.dto.response.PageResponse;
import com.sabibiz.entity.Customer;
import com.sabibiz.entity.User;
import com.sabibiz.exception.ResourceNotFoundException;
import com.sabibiz.mapper.CustomerMapper;
import com.sabibiz.repository.CustomerRepository;
import com.sabibiz.service.CustomerService;
import com.sabibiz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final UserService userService;

    @Override
    public CustomerResponse create(Long businessId, CustomerRequest request) {
        if (customerRepository.existsByBusinessIdAndCustomerNumber(businessId, request.getCustomerNumber())) {
            throw new IllegalArgumentException("Customer number already exists");
        }
        if (request.getEmail() != null && customerRepository.existsByBusinessIdAndEmail(businessId, request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (request.getPhoneNumber() != null && customerRepository.existsByBusinessIdAndPhoneNumber(businessId, request.getPhoneNumber())) {
            throw new IllegalArgumentException("Phone number already exists");
        }

        Customer customer = customerMapper.toEntity(request);
        customer.setBusiness(userService.getBusinessEntity(businessId));
        return customerMapper.toResponse(customerRepository.save(customer));
    }

    @Override
    public CustomerResponse getById(Long businessId, Long id) {
        Customer customer = customerRepository.findByBusinessIdAndId(businessId, id)
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        return customerMapper.toResponse(customer);
    }

    @Override
    public CustomerResponse update(Long businessId, Long id, CustomerRequest request) {
        Customer customer = customerRepository.findByBusinessIdAndId(businessId, id)
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));

        if (!customer.getCustomerNumber().equals(request.getCustomerNumber())
            && customerRepository.existsByBusinessIdAndCustomerNumber(businessId, request.getCustomerNumber())) {
            throw new IllegalArgumentException("Customer number already exists");
        }
        if (request.getEmail() != null && !request.getEmail().equals(customer.getEmail())
            && customerRepository.existsByBusinessIdAndEmail(businessId, request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (request.getPhoneNumber() != null && !request.getPhoneNumber().equals(customer.getPhoneNumber())
            && customerRepository.existsByBusinessIdAndPhoneNumber(businessId, request.getPhoneNumber())) {
            throw new IllegalArgumentException("Phone number already exists");
        }

        customerMapper.updateEntity(customer, request);
        return customerMapper.toResponse(customerRepository.save(customer));
    }

    @Override
    public void delete(Long businessId, Long id) {
        Customer customer = customerRepository.findByBusinessIdAndId(businessId, id)
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        customerRepository.delete(customer);
    }

    @Override
    public PageResponse<CustomerResponse> getAll(Long businessId, Pageable pageable) {
        Page<Customer> page = customerRepository.findByBusinessId(businessId, pageable);
        return PageResponse.of(page.map(customerMapper::toResponse));
    }

    @Override
    public PageResponse<CustomerResponse> search(Long businessId, String query, Pageable pageable) {
        Page<Customer> page = customerRepository.searchCustomers(businessId, query, pageable);
        return PageResponse.of(page.map(customerMapper::toResponse));
    }

    @Override
    public CustomerResponse getByCustomerNumber(Long businessId, String customerNumber) {
        Customer customer = customerRepository.findByBusinessIdAndCustomerNumber(businessId, customerNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found with number: " + customerNumber));
        return customerMapper.toResponse(customer);
    }
}