package com.sabibiz.controller;

import com.sabibiz.dto.request.BusinessRequest;
import com.sabibiz.dto.response.BusinessResponse;
import com.sabibiz.dto.response.PageResponse;
import com.sabibiz.service.BusinessService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/businesses")
@RequiredArgsConstructor
public class BusinessController {

    private final BusinessService businessService;

    @PostMapping
    public ResponseEntity<BusinessResponse> create(@Valid @RequestBody BusinessRequest request) {
        BusinessResponse response = businessService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusinessResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(businessService.getById(id));
    }

    @GetMapping("/number/{businessNumber}")
    public ResponseEntity<BusinessResponse> getByBusinessNumber(@PathVariable String businessNumber) {
        return ResponseEntity.ok(businessService.getByBusinessNumber(businessNumber));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusinessResponse> update(@PathVariable Long id, @Valid @RequestBody BusinessRequest request) {
        return ResponseEntity.ok(businessService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        businessService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PageResponse<BusinessResponse>> getAll(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(businessService.getAll(pageable));
    }

    @GetMapping("/active")
    public ResponseEntity<PageResponse<BusinessResponse>> getActive(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(businessService.getActive(pageable));
    }
}