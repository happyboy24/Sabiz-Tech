package com.sabibiz.service.impl;

import com.sabibiz.dto.request.PaymentRequest;
import com.sabibiz.dto.response.PageResponse;
import com.sabibiz.dto.response.PaymentResponse;
import com.sabibiz.entity.*;
import com.sabibiz.enums.PaymentStatus;
import com.sabibiz.enums.PaymentType;
import com.sabibiz.exception.ResourceNotFoundException;
import com.sabibiz.repository.*;
import com.sabibiz.service.PaymentService;
import com.sabibiz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BusinessRepository businessRepository;
    private final CustomerRepository customerRepository;
    private final SupplierRepository supplierRepository;
    private final SaleRepository saleRepository;
    private final DebtRepository debtRepository;
    private final ExpenseRepository expenseRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final UserService userService;

    @Override
    public PaymentResponse create(Long businessId, PaymentRequest request) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(() -> new ResourceNotFoundException("Business not found with id: " + businessId));

        Payment payment = new Payment();
        payment.setBusiness(business);
        payment.setPaymentNumber(request.getPaymentNumber() != null ? request.getPaymentNumber() : "PAY-" + System.currentTimeMillis());
        payment.setPaymentType(request.getPaymentType());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setAmount(request.getAmount());
        payment.setTaxAmount(request.getTaxAmount());
        payment.setPaymentDate(request.getPaymentDate() != null ? request.getPaymentDate() : LocalDate.now());
        payment.setDueDate(request.getDueDate());
        payment.setReferenceNumber(request.getReferenceNumber());
        payment.setReceivedFrom(request.getReceivedFrom());
        payment.setNotes(request.getNotes());
        payment.setReceiptUrl(request.getReceiptUrl());
        payment.setStatus(request.getStatus() != null ? request.getStatus() : PaymentStatus.COMPLETED);
        payment.setCreatedBy(userService.getCurrentUser());

        if (request.getCustomerId() != null) {
            payment.setCustomer(customerRepository.findById(request.getCustomerId()).orElse(null));
        }
        if (request.getSupplierId() != null) {
            payment.setSupplier(supplierRepository.findById(request.getSupplierId()).orElse(null));
        }
        if (request.getSaleId() != null) {
            payment.setSale(saleRepository.findById(request.getSaleId()).orElse(null));
        }
        if (request.getDebtId() != null) {
            payment.setDebt(debtRepository.findById(request.getDebtId()).orElse(null));
        }
        if (request.getExpenseId() != null) {
            payment.setExpense(expenseRepository.findById(request.getExpenseId()).orElse(null));
        }
        if (request.getPurchaseOrderId() != null) {
            payment.setPurchaseOrder(purchaseOrderRepository.findById(request.getPurchaseOrderId()).orElse(null));
        }

        payment.calculateTotal();
        payment = paymentRepository.save(payment);
        return toResponse(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getById(Long businessId, Long id) {
        Payment payment = paymentRepository.findByBusinessIdAndId(businessId, id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
        return toResponse(payment);
    }

    @Override
    public void delete(Long businessId, Long id) {
        Payment payment = paymentRepository.findByBusinessIdAndId(businessId, id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
        paymentRepository.delete(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<PaymentResponse> getAll(Long businessId, Pageable pageable) {
        Page<Payment> page = paymentRepository.findByBusinessId(businessId, pageable);
        return PageResponse.of(page.map(this::toResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<PaymentResponse> getByType(Long businessId, PaymentType type, Pageable pageable) {
        List<Payment> payments = paymentRepository.findByBusinessIdAndPaymentType(businessId, type);
        Page<Payment> page = new PageImpl<>(payments, pageable, payments.size());
        return PageResponse.of(page.map(this::toResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<PaymentResponse> getByStatus(Long businessId, PaymentStatus status, Pageable pageable) {
        List<Payment> payments = paymentRepository.findByBusinessIdAndStatus(businessId, status);
        Page<Payment> page = new PageImpl<>(payments, pageable, payments.size());
        return PageResponse.of(page.map(this::toResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<PaymentResponse> getByDateRange(Long businessId, LocalDate start, LocalDate end, Pageable pageable) {
        List<Payment> payments = paymentRepository.findByBusinessIdAndPaymentDateBetween(businessId, start, end);
        Page<Payment> page = new PageImpl<>(payments, pageable, payments.size());
        return PageResponse.of(page.map(this::toResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<PaymentResponse> getBySale(Long businessId, Long saleId, Pageable pageable) {
        List<Payment> payments = paymentRepository.findByBusinessIdAndSaleId(businessId, saleId);
        Page<Payment> page = new PageImpl<>(payments, pageable, payments.size());
        return PageResponse.of(page.map(this::toResponse));
    }

    @Override
    public PaymentResponse confirm(Long businessId, Long id) {
        Payment payment = paymentRepository.findByBusinessIdAndId(businessId, id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
        payment.confirm();
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            payment.setConfirmedBy(currentUser.getId());
        }
        payment = paymentRepository.save(payment);
        return toResponse(payment);
    }

    @Override
    public PaymentResponse cancel(Long businessId, Long id) {
        Payment payment = paymentRepository.findByBusinessIdAndId(businessId, id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
        payment.cancel();
        payment = paymentRepository.save(payment);
        return toResponse(payment);
    }

    private PaymentResponse toResponse(Payment p) {
        return PaymentResponse.builder()
                .id(p.getId())
                .paymentNumber(p.getPaymentNumber())
                .paymentType(p.getPaymentType())
                .paymentMethod(p.getPaymentMethod())
                .customerId(p.getCustomer() != null ? p.getCustomer().getId() : null)
                .customerName(p.getCustomer() != null ? p.getCustomer().getName() : null)
                .supplierId(p.getSupplier() != null ? p.getSupplier().getId() : null)
                .supplierName(p.getSupplier() != null ? p.getSupplier().getName() : null)
                .businessId(p.getBusiness() != null ? p.getBusiness().getId() : null)
                .businessName(p.getBusiness() != null ? p.getBusiness().getName() : null)
                .saleId(p.getSale() != null ? p.getSale().getId() : null)
                .saleNumber(p.getSale() != null ? p.getSale().getSaleNumber() : null)
                .debtId(p.getDebt() != null ? p.getDebt().getId() : null)
                .debtNumber(p.getDebt() != null ? p.getDebt().getDebtNumber() : null)
                .purchaseOrderId(p.getPurchaseOrder() != null ? p.getPurchaseOrder().getId() : null)
                .purchaseOrderNumber(p.getPurchaseOrder() != null ? p.getPurchaseOrder().getOrderNumber() : null)
                .expenseId(p.getExpense() != null ? p.getExpense().getId() : null)
                .expenseNumber(p.getExpense() != null ? p.getExpense().getExpenseNumber() : null)
                .amount(p.getAmount())
                .taxAmount(p.getTaxAmount())
                .totalAmount(p.getTotalAmount())
                .paymentDate(p.getPaymentDate())
                .dueDate(p.getDueDate())
                .referenceNumber(p.getReferenceNumber())
                .receivedFrom(p.getReceivedFrom())
                .notes(p.getNotes())
                .receiptUrl(p.getReceiptUrl())
                .status(p.getStatus())
                .createdBy(p.getCreatedBy() != null ? p.getCreatedBy().getId() : null)
                .createdByName(p.getCreatedBy() != null ? p.getCreatedBy().getFullName() : null)
                .confirmedAt(p.getConfirmedAt())
                .confirmedBy(p.getConfirmedBy())
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt())
                .build();
    }
}
