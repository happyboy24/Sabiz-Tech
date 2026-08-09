package com.sabibiz.mapper;

import com.sabibiz.dto.request.BusinessRequest;
import com.sabibiz.dto.response.BusinessResponse;
import com.sabibiz.entity.Business;
import org.springframework.stereotype.Component;

@Component
public class BusinessMapper {

    public Business toEntity(BusinessRequest request) {
        Business business = new Business();
        business.setName(request.getName());
        business.setTradeName(request.getTradeName());
        business.setBusinessNumber(request.getBusinessNumber());
        business.setRegistrationNumber(request.getRegistrationNumber());
        business.setTaxNumber(request.getTaxNumber());
        business.setAddress(request.getAddress());
        business.setCity(request.getCity());
        business.setState(request.getState());
        business.setCountry(request.getCountry());
        business.setPostalCode(request.getPostalCode());
        business.setPhoneNumber(request.getPhoneNumber());
        business.setAltPhoneNumber(request.getAltPhoneNumber());
        business.setEmail(request.getEmail());
        business.setWebsite(request.getWebsite());
        business.setCurrencyCode(request.getCurrencyCode() != null ? request.getCurrencyCode() : "USD");
        business.setCurrencySymbol(request.getCurrencySymbol() != null ? request.getCurrencySymbol() : "$");
        business.setTimezone(request.getTimezone() != null ? request.getTimezone() : "UTC");
        business.setDateFormat(request.getDateFormat() != null ? request.getDateFormat() : "yyyy-MM-dd");
        business.setTimeFormat(request.getTimeFormat() != null ? request.getTimeFormat() : "HH:mm");
        business.setNumberFormat(request.getNumberFormat() != null ? request.getNumberFormat() : "#,##0.00");
        business.setMaxUsers(request.getMaxUsers() != null ? request.getMaxUsers() : 10);
        business.setMaxProducts(request.getMaxProducts() != null ? request.getMaxProducts() : 1000);
        business.setAllowMultiBranch(request.getAllowMultiBranch() != null ? request.getAllowMultiBranch() : false);
        business.setAllowMultiCurrency(request.getAllowMultiCurrency() != null ? request.getAllowMultiCurrency() : false);
        business.setSettings(request.getSettings());
        business.setIsActive(true);
        business.setSubscriptionStatus(Business.SubscriptionStatus.TRIAL);
        return business;
    }

    public BusinessResponse toResponse(Business business) {
        BusinessResponse response = new BusinessResponse();
        response.setId(business.getId());
        response.setName(business.getName());
        response.setTradeName(business.getTradeName());
        response.setBusinessNumber(business.getBusinessNumber());
        response.setRegistrationNumber(business.getRegistrationNumber());
        response.setTaxNumber(business.getTaxNumber());
        response.setAddress(business.getAddress());
        response.setCity(business.getCity());
        response.setState(business.getState());
        response.setCountry(business.getCountry());
        response.setPostalCode(business.getPostalCode());
        response.setPhoneNumber(business.getPhoneNumber());
        response.setAltPhoneNumber(business.getAltPhoneNumber());
        response.setEmail(business.getEmail());
        response.setWebsite(business.getWebsite());
        response.setLogoUrl(business.getLogoUrl());
        response.setCurrencyCode(business.getCurrencyCode());
        response.setCurrencySymbol(business.getCurrencySymbol());
        response.setTimezone(business.getTimezone());
        response.setDateFormat(business.getDateFormat());
        response.setTimeFormat(business.getTimeFormat());
        response.setNumberFormat(business.getNumberFormat());
        response.setIsActive(business.getIsActive());
        response.setSubscriptionStatus(business.getSubscriptionStatus());
        response.setSubscriptionExpiresAt(business.getSubscriptionExpiresAt());
        response.setTrialEndsAt(business.getTrialEndsAt());
        response.setMaxUsers(business.getMaxUsers());
        response.setMaxProducts(business.getMaxProducts());
        response.setAllowMultiBranch(business.getAllowMultiBranch());
        response.setAllowMultiCurrency(business.getAllowMultiCurrency());
        response.setSettings(business.getSettings());
        response.setCreatedAt(business.getCreatedAt());
        response.setUpdatedAt(business.getUpdatedAt());
        return response;
    }

    public void updateEntity(Business business, BusinessRequest request) {
        business.setName(request.getName());
        business.setTradeName(request.getTradeName());
        business.setBusinessNumber(request.getBusinessNumber());
        business.setRegistrationNumber(request.getRegistrationNumber());
        business.setTaxNumber(request.getTaxNumber());
        business.setAddress(request.getAddress());
        business.setCity(request.getCity());
        business.setState(request.getState());
        business.setCountry(request.getCountry());
        business.setPostalCode(request.getPostalCode());
        business.setPhoneNumber(request.getPhoneNumber());
        business.setAltPhoneNumber(request.getAltPhoneNumber());
        business.setEmail(request.getEmail());
        business.setWebsite(request.getWebsite());
        business.setCurrencyCode(request.getCurrencyCode());
        business.setCurrencySymbol(request.getCurrencySymbol());
        business.setTimezone(request.getTimezone());
        business.setDateFormat(request.getDateFormat());
        business.setTimeFormat(request.getTimeFormat());
        business.setNumberFormat(request.getNumberFormat());
        business.setMaxUsers(request.getMaxUsers());
        business.setMaxProducts(request.getMaxProducts());
        business.setAllowMultiBranch(request.getAllowMultiBranch());
        business.setAllowMultiCurrency(request.getAllowMultiCurrency());
        business.setSettings(request.getSettings());
    }
}