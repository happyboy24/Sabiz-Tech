package com.sabibiz.dto.response;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {

    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Boolean isActive;
    private Integer sortOrder;
    private Long parentId;
    private String parentName;
    private List<CategoryResponse> children;
    private Long businessId;
    private String businessName;
    private Integer productCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}