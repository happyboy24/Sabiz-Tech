package com.sabibiz.entity;

import com.sabibiz.enums.MovementType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockMovement extends BaseEntity {

    @NotBlank
    @Size(max = 50)
    @Column(name = "movement_number", nullable = false, unique = true)
    private String movementNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "movement_type", nullable = false)
    private MovementType movementType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @NotNull
    @Column(nullable = false)
    private Integer quantity = 0;

    @Column(name = "unit_cost", precision = 19, scale = 4)
    private BigDecimal unitCost;

    @Column(name = "total_cost", precision = 19, scale = 4)
    private BigDecimal totalCost;

    @Column(name = "stock_before", nullable = false)
    private Integer stockBefore = 0;

    @Column(name = "stock_after", nullable = false)
    private Integer stockAfter = 0;

    @Size(max = 50)
    @Column(name = "reference_type")
    private String referenceType;

    @Column(name = "reference_id")
    private Long referenceId;

    @Size(max = 100)
    @Column(name = "reference_number")
    private String referenceNumber;

    @Size(max = 20)
    @Column(name = "unit_of_measure")
    private String unitOfMeasure;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(name = "movement_date", nullable = false)
    private LocalDateTime movementDate = LocalDateTime.now();
}