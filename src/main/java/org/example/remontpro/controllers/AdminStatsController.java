package org.example.remontpro.controllers;


import lombok.RequiredArgsConstructor;
import org.example.remontpro.dto.EmployeeOrderStatsDto;
import org.example.remontpro.services.AdminStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/stats")

public class AdminStatsController {
    private final AdminStatsService adminStatsService;

    @Autowired AdminStatsController(final AdminStatsService adminStatsService) {
        this.adminStatsService = adminStatsService;
    }

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeOrderStatsDto>> getAllEmployeeStats() {
        return ResponseEntity.ok(adminStatsService.getAllEmployeeStats());
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeOrderStatsDto> getEmployeeStats(@PathVariable Long id) {
        return ResponseEntity.ok(adminStatsService.getStatsForEmployee(id));
    }
    @GetMapping("/orders-by-status")
    public ResponseEntity<Map<String, Long>> getOrdersCountByStatus() {
        return ResponseEntity.ok(adminStatsService.getOrderCountsByStatus());
    }

}
