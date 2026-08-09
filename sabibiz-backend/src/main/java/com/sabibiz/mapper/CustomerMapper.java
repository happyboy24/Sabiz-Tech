package com.sabibiz.mapper;

import com.sabibiz.dto.request.CustomerRequest;
import com.sabibiz.dto.response.CustomerResponse;
import com.sabibiz.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toEntity(CustomerRequest request) {
        Customer customer = new Customer();
        customer.setCustomerNumber(request.getCustomerNumber());
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setAltPhoneNumber(request.getAltPhoneNumber());
        customer.setAddress(request.getAddress());
        customer.setCity(request.getCity());
        customer.setCountry(request.getCountry());
        customer.setPostalCode(request.getPostalCode());
        customer.setTaxNumber(request.getTaxNumber());
        customer.setCreditLimit(request.getCreditLimit() != null ? request.getCreditLimit() : java.math.BigDecimal.ZERO);
        customer.setPaymentTermsDays(request.getPaymentTermsDays() != null ? request.getPaymentTermsDays() : 30);
        customer.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
        customer.setNotes(request.getNotes());
        return customer;
    }

    public CustomerResponse toResponse(Customer customer) {
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setCustomerNumber(customer.getCustomerNumber());
        response.setName(customer.getName());
        response.setEmail(customer.getEmail());
        response.setPhoneNumber(customer.getPhoneNumber());
        response.setAltPhoneNumber(customer.getAltPhoneNumber());
        response.setAddress(customer.getAddress());
        response.setCity(customer.getCity());
        response.setCountry(customer.getCountry());
        response.setPostalCode(customer.getPostalCode());
        response.setTaxNumber(customer.getTaxNumber());
        response.setCreditLimit(customer.getCreditLimit());
        response.setCurrentBalance(customer.getCurrentBalance());
        response.setAvailableCredit(customer.getAvailableCredit());
        response.setPaymentTermsDays(customer.getPaymentTermsDays());
        response.setIsActive(customer.getIsActive());
        response.setIsBlacklisted(customer.getIsBlacklisted());
        response.setBlacklistReason(customer.getBlacklistReason());
        response.setLastPurchaseDate(customer.getLastPurchaseDate());
        response.setLastPaymentDate(customer.getLastPaymentDate());
        response.setTotalPurchases(customer.getTotalPurchases());
        response.setTotalPayments(customer.getTotalPayments());
        response.setLoyaltyPoints(customer.getLoyaltyPoints());
        response.setNotes(customer.getNotes());
        if (customer.getBusiness() != null) {
            response.setBusinessId(customer.getBusiness().getId());
            response.setBusinessName(customer.getBusiness().getName());
        }
        response.setCreatedAt(customer.getCreatedAt());
        response.setUpdatedAt(customer.getUpdatedAt());
        return response;
    }

    public void updateEntity(Customer customer, CustomerRequest request) {
        customer.setCustomerNumber(request.getCustomerNumber());
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setAltPhoneNumber(request.getAltPhoneNumber());
        customer.setAddress(request.getAddress());
        customer.setCity(request.getCity());
        customer.setCountry(request.getCountry());
        customer.setPostalCode(request.getPostalCode());
        customer.setTaxNumber(request.getTaxNumber());
        if (request.getCreditLimit() != null) {
            customer.setCreditLimit(request.getCreditLimit());
        }
        if (request.getPaymentTermsDays() != null) {
            customer.setPaymentTermsDays(request.getPaymentTermsDays());
        }
        if (request.getIsActive() != null) {
            customer.setIsActive(request.getIsActive());
        }
        customer.setNotes(request.getNotes());
    }
}