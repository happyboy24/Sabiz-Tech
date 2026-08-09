package com.sabibiz.service.impl;

import com.sabibiz.dto.request.DebtRequest;
import com.sabibiz.dto.response.DebtResponse;
import com.sabibiz.dto.response.PageResponse;
import com.sabibiz.entity.*;
import com.sabibiz.enums.DebtStatus;
import com.sabibiz.enums.DebtType;
import com.sabibiz.exception.ResourceNotFoundException;
import com.sabibiz.repository.*;
import com.sabibiz.service.DebtService;
import com.sabibiz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DebtServiceImpl implements DebtService {

    private final DebtRepository debtRepository;
    private final BusinessRepository businessRepository;
    private final CustomerRepository customerRepository;
    private final SupplierRepository supplierRepository;
    private final SaleRepository saleRepository;
    private final UserService userService;

    @Override
    public DebtResponse create(Long businessId, DebtRequest request) {
        Business business = businessRepository.findById(businessId)
            .orElseThrow(() -> new ResourceNotFoundException("Business not found with id: " + businessId));

        Debt debt = new Debt();
        debt.setBusiness(business);
        debt.setDebtNumber(request.getDebtNumber() != null ? request.getDebtNumber()
            : "DEBT-" + System.currentTimeMillis());
        debt.setDebtType(request.getDebtType());
        debt.setStatus(request.getStatus() != null ? request.getStatus() : DebtStatus.PENDING);
        debt.setOriginalAmount(request.getOriginalAmount());
        debt.setPaidAmount(request.getPaidAmount() != null ? request.getPaidAmount() : BigDecimal.ZERO);
        debt.setInterestRate(request.getInterestRate() != null ? request.getInterestRate() : BigDecimal.ZERO);
        debt.setDueDate(request.getDueDate());
        debt.setPaymentTermsDays(request.getPaymentTermsDays() != null ? request.getPaymentTermsDays() : 30);
        debt.setNotes(request.getNotes());
        debt.setCreatedBy(userService.getCurrentUser());

        if (request.getCustomerId() != null) {
            debt.setCustomer(customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found")));
        }
        if (request.getSupplierId() != null) {
            debt.setSupplier(supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found")));
        }

        debt.calculateBalance();
        debt.updateOverdueStatus();
        debt = debtRepository.save(debt);
        return toResponse(debt);
    }

    @Override
    @Transactional(readOnly = true)
    public DebtResponse getById(Long businessId, Long id) {
        Debt debt = debtRepository.findByBusinessIdAndId(businessId, id)
            .orElseThrow(() -> new ResourceNotFoundException("Debt not found with id: " + id));
        return toResponse(debt);
    }

    @Override
    public DebtResponse update(Long businessId, Long id, DebtRequest request) {
        Debt debt = debtRepository.findByBusinessIdAndId(businessId, id)
            .orElseThrow(() -> new ResourceNotFoundException("Debt not found with id: " + id));

        debt.setDebtType(request.getDebtType());
        if (request.getStatus() != null) debt.setStatus(request.getStatus());
        debt.setOriginalAmount(request.getOriginalAmount());
        if (request.getPaidAmount() != null) debt.setPaidAmount(request.getPaidAmount());
        if (request.getInterestRate() != null) debt.setInterestRate(request.getInterestRate());
        debt.setDueDate(request.getDueDate());
        if (request.getPaymentTermsDays() != null) debt.setPaymentTermsDays(request.getPaymentTermsDays());
        debt.setNotes(request.getNotes());

        if (request.getCustomerId() != null) {
            debt.setCustomer(customerRepository.findById(request.getCustomerId()).orElse(null));
        }
        if (request.getSupplierId() != null) {
            debt.setSupplier(supplierRepository.findById(request.getSupplierId()).orElse(null));
        }

        debt.calculateBalance();
        debt.updateOverdueStatus();
        debt = debtRepository.save(debt);
        return toResponse(debt);
    }

    @Override
    public void delete(Long businessId, Long id) {
        Debt debt = debtRepository.findByBusinessIdAndId(businessId, id)
            .orElseThrow(() -> new ResourceNotFoundException("Debt not found with id: " + id));
        debtRepository.delete(debt);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<DebtResponse> getAll(Long businessId, Pageable pageable) {
        Page<Debt> page = debtRepository.findByBusinessId(businessId, pageable);
        return PageResponse.of(page.map(this::toResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<DebtResponse> getByType(Long businessId, DebtType type, Pageable pageable) {
        List<Debt> debts = debtRepository.findByBusinessIdAndDebtType(businessId, type);
        Page<Debt> page = new PageImpl<>(debts, pageable, debts.size());
        return PageResponse.of(page.map(this::toResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<DebtResponse> getByStatus(Long businessId, DebtStatus status, Pageable pageable) {
        List<Debt> debts = debtRepository.findByBusinessIdAndStatus(businessId, status);
        Page<Debt> page = new PageImpl<>(debts, pageable, debts.size());
        return PageResponse.of(page.map(this::toResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<DebtResponse> getByCustomer(Long businessId, Long customerId, Pageable pageable) {
        List<Debt> debts = debtRepository.findByBusinessIdAndCustomerId(businessId, customerId);
        Page<Debt> page = new PageImpl<>(debts, pageable, debts.size());
        return PageResponse.of(page.map(this::toResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<DebtResponse> getBySupplier(Long businessId, Long supplierId, Pageable pageable) {
        List<Debt> debts = debtRepository.findByBusinessIdAndSupplierId(businessId, supplierId);
        Page<Debt> page = new PageImpl<>(debts, pageable, debts.size());
        return PageResponse.of(page.map(this::toResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<DebtResponse> getOverdue(Long businessId, Pageable pageable) {
        List<Debt> debts = debtRepository.findByBusinessIdAndIsOverdue(businessId, true);
        Page<Debt> page = new PageImpl<>(debts, pageable, debts.size());
        return PageResponse.of(page.map(this::toResponse));
    }

    @Override
    public DebtResponse recordPayment(Long businessId, Long id, BigDecimal paymentAmount) {
        Debt debt = debtRepository.findByBusinessIdAndId(businessId, id)
            .orElseThrow(() -> new ResourceNotFoundException("Debt not found with id: " + id));

        if (debt.getStatus() == DebtStatus.PAID || debt.getStatus() == DebtStatus.CANCELLED) {
            throw new IllegalStateException("Cannot record payment for a " + debt.getStatus() + " debt");
        }

        BigDecimal currentPaid = debt.getPaidAmount() != null ? debt.getPaidAmount() : BigDecimal.ZERO;
        debt.setPaidAmount(currentPaid.add(paymentAmount));
        debt.setLastPaymentDate(LocalDate.now());
        debt.calculateBalance();
        debt.updateOverdueStatus();
        debt = debtRepository.save(debt);
        return toResponse(debt);
    }

    @Override
    public void updateOverdueStatuses(Long businessId) {
        List<Debt> activeDebts = debtRepository.findByBusinessId(businessId).stream()
            .filter(d -> d.getStatus() != DebtStatus.PAID && d.getStatus() != DebtStatus.CANCELLED)
            .collect(Collectors.toList());

        for (Debt debt : activeDebts) {
            debt.updateOverdueStatus();
        }
        debtRepository.saveAll(activeDebts);
    }

    private DebtResponse toResponse(Debt d) {
        return DebtResponse.builder()
            .id(d.getId())
            .debtNumber(d.getDebtNumber())
            .debtType(d.getDebtType())
            .status(d.getStatus())
            .customerId(d.getCustomer() != null ? d.getCustomer().getId() : null)
            .customerName(d.getCustomer() != null ? d.getCustomer().getName() : null)
            .supplierId(d.getSupplier() != null ? d.getSupplier().getId() : null)
            .supplierName(d.getSupplier() != null ? d.getSupplier().getName() : null)
            .businessId(d.getBusiness() != null ? d.getBusiness().getId() : null)
            .businessName(d.getBusiness() != null ? d.getBusiness().getName() : null)
            .saleId(d.getSale() != null ? d.getSale().getId() : null)
            .saleNumber(d.getSale() != null ? d.getSale().getSaleNumber() : null)
            .purchaseOrderId(d.getPurchaseOrder() != null ? d.getPurchaseOrder().getId() : null)
            .purchaseOrderNumber(d.getPurchaseOrder() != null ? d.getPurchaseOrder().getOrderNumber() : null)
            .originalAmount(d.getOriginalAmount())
            .paidAmount(d.getPaidAmount())
            .balanceAmount(d.getBalanceAmount())
            .interestRate(d.getInterestRate())
            .interestAmount(d.getInterestAmount())
            .dueDate(d.getDueDate())
            .paidDate(d.getPaidDate())
            .lastPaymentDate(d.getLastPaymentDate())
            .nextDueDate(d.getNextDueDate())
            .isOverdue(d.getIsOverdue())
            .daysOverdue(d.getDaysOverdue())
            .notes(d.getNotes())
            .createdBy(d.getCreatedBy() != null ? d.getCreatedBy().getId() : null)
            .createdByName(d.getCreatedBy() != null ? d.getCreatedBy().getFullName() : null)
            .paymentTermsDays(d.getPaymentTermsDays())
            .createdAt(d.getCreatedAt())
            .updatedAt(d.getUpdatedAt())
            .build();
    }
}
