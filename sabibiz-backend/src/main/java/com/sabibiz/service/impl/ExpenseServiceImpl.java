package com.sabibiz.service.impl;

import com.sabibiz.dto.request.ExpenseRequest;
import com.sabibiz.dto.response.ExpenseResponse;
import com.sabibiz.dto.response.PageResponse;
import com.sabibiz.entity.Business;
import com.sabibiz.entity.Expense;
import com.sabibiz.entity.Supplier;
import com.sabibiz.enums.ExpenseCategory;
import com.sabibiz.enums.PaymentStatus;
import com.sabibiz.exception.ResourceNotFoundException;
import com.sabibiz.repository.BusinessRepository;
import com.sabibiz.repository.ExpenseRepository;
import com.sabibiz.repository.SupplierRepository;
import com.sabibiz.service.ExpenseService;
import com.sabibiz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final BusinessRepository businessRepository;
    private final SupplierRepository supplierRepository;
    private final UserService userService;

    @Override
    public ExpenseResponse create(Long businessId, ExpenseRequest request) {
        Business business = businessRepository.findById(businessId)
            .orElseThrow(() -> new ResourceNotFoundException("Business not found with id: " + businessId));

        Expense expense = new Expense();
        expense.setBusiness(business);
        expense.setExpenseNumber(request.getExpenseNumber() != null ? request.getExpenseNumber()
            : "EXP-" + System.currentTimeMillis());
        expense.setCategory(request.getCategory());
        expense.setAmount(request.getAmount());
        expense.setTaxAmount(request.getTaxAmount());
        expense.setExpenseDate(request.getExpenseDate() != null ? request.getExpenseDate() : LocalDate.now());
        expense.setDueDate(request.getDueDate());
        expense.setPaymentStatus(request.getPaymentStatus() != null ? request.getPaymentStatus() : PaymentStatus.UNPAID);
        expense.setPaymentMethod(request.getPaymentMethod());
        expense.setReferenceNumber(request.getReferenceNumber());
        expense.setDescription(request.getDescription());
        expense.setReceiptUrl(request.getReceiptUrl());
        expense.setIsRecurring(request.getIsRecurring() != null ? request.getIsRecurring() : false);
        expense.setRecurrencePattern(request.getRecurrencePattern());
        expense.setNextDueDate(request.getNextDueDate());
        expense.setCreatedBy(userService.getCurrentUser());

        if (request.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + request.getSupplierId()));
            expense.setSupplier(supplier);
        }

        expense.calculateTotal();
        expense = expenseRepository.save(expense);
        return toResponse(expense);
    }

    @Override
    @Transactional(readOnly = true)
    public ExpenseResponse getById(Long businessId, Long id) {
        Expense expense = expenseRepository.findByBusinessIdAndId(businessId, id)
            .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));
        return toResponse(expense);
    }

    @Override
    public ExpenseResponse update(Long businessId, Long id, ExpenseRequest request) {
        Expense expense = expenseRepository.findByBusinessIdAndId(businessId, id)
            .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));

        expense.setCategory(request.getCategory());
        expense.setAmount(request.getAmount());
        expense.setTaxAmount(request.getTaxAmount());
        expense.setExpenseDate(request.getExpenseDate() != null ? request.getExpenseDate() : expense.getExpenseDate());
        expense.setDueDate(request.getDueDate());
        if (request.getPaymentStatus() != null) expense.setPaymentStatus(request.getPaymentStatus());
        expense.setPaymentMethod(request.getPaymentMethod());
        expense.setReferenceNumber(request.getReferenceNumber());
        expense.setDescription(request.getDescription());
        expense.setReceiptUrl(request.getReceiptUrl());
        if (request.getIsRecurring() != null) expense.setIsRecurring(request.getIsRecurring());
        expense.setRecurrencePattern(request.getRecurrencePattern());
        expense.setNextDueDate(request.getNextDueDate());

        if (request.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));
            expense.setSupplier(supplier);
        } else {
            expense.setSupplier(null);
        }

        expense.calculateTotal();
        expense = expenseRepository.save(expense);
        return toResponse(expense);
    }

    @Override
    public void delete(Long businessId, Long id) {
        Expense expense = expenseRepository.findByBusinessIdAndId(businessId, id)
            .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));
        expenseRepository.delete(expense);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ExpenseResponse> getAll(Long businessId, Pageable pageable) {
        Page<Expense> page = expenseRepository.findByBusinessId(businessId, pageable);
        return PageResponse.of(page.map(this::toResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ExpenseResponse> getByCategory(Long businessId, ExpenseCategory category, Pageable pageable) {
        Page<Expense> page = expenseRepository.findByBusinessIdAndCategory(businessId, category, pageable);
        return PageResponse.of(page.map(this::toResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ExpenseResponse> getByPaymentStatus(Long businessId, PaymentStatus status, Pageable pageable) {
        Page<Expense> page = expenseRepository.findByBusinessIdAndPaymentStatus(businessId, status, pageable);
        return PageResponse.of(page.map(this::toResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ExpenseResponse> getByDateRange(Long businessId, LocalDate start, LocalDate end, Pageable pageable) {
        Page<Expense> page = expenseRepository.findByBusinessIdAndExpenseDateBetween(businessId, start, end, pageable);
        return PageResponse.of(page.map(this::toResponse));
    }

    @Override
    public ExpenseResponse markAsPaid(Long businessId, Long id, LocalDate paidDate) {
        Expense expense = expenseRepository.findByBusinessIdAndId(businessId, id)
            .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));
        expense.setPaymentStatus(PaymentStatus.COMPLETED);
        expense.setPaidDate(paidDate != null ? paidDate : LocalDate.now());
        expense = expenseRepository.save(expense);
        return toResponse(expense);
    }

    private ExpenseResponse toResponse(Expense e) {
        return ExpenseResponse.builder()
            .id(e.getId())
            .expenseNumber(e.getExpenseNumber())
            .category(e.getCategory())
            .categoryDisplayName(e.getCategory() != null ? e.getCategory().name() : null)
            .supplierId(e.getSupplier() != null ? e.getSupplier().getId() : null)
            .supplierName(e.getSupplier() != null ? e.getSupplier().getName() : null)
            .businessId(e.getBusiness() != null ? e.getBusiness().getId() : null)
            .businessName(e.getBusiness() != null ? e.getBusiness().getName() : null)
            .amount(e.getAmount())
            .taxAmount(e.getTaxAmount())
            .totalAmount(e.getTotalAmount())
            .expenseDate(e.getExpenseDate())
            .dueDate(e.getDueDate())
            .paidDate(e.getPaidDate())
            .paymentStatus(e.getPaymentStatus())
            .paymentMethod(e.getPaymentMethod())
            .referenceNumber(e.getReferenceNumber())
            .description(e.getDescription())
            .receiptUrl(e.getReceiptUrl())
            .createdBy(e.getCreatedBy() != null ? e.getCreatedBy().getId() : null)
            .createdByName(e.getCreatedBy() != null ? e.getCreatedBy().getFullName() : null)
            .isRecurring(e.getIsRecurring())
            .recurrencePattern(e.getRecurrencePattern())
            .nextDueDate(e.getNextDueDate())
            .createdAt(e.getCreatedAt())
            .updatedAt(e.getUpdatedAt())
            .build();
    }
}
