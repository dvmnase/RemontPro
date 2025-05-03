package org.example.remontpro.controllers;

import lombok.RequiredArgsConstructor;
import org.example.remontpro.dto.ServiceResponseDTO;
import org.example.remontpro.exceptions.ResourceNotFoundException;
import org.example.remontpro.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/user/services")
public class UserServiceController {

    private final ServiceService userServiceService;

    @Autowired
    public UserServiceController(final ServiceService userServiceService) {
        this.userServiceService = userServiceService;
    }

    // 1. Получить все услуги
    @GetMapping
    public ResponseEntity<List<ServiceResponseDTO>> getAllServices() {
        return ResponseEntity.ok(userServiceService.getAllServices());
    }

    // 2. Получить услугу по ID
    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponseDTO> getServiceById(@PathVariable Long id) {
        return ResponseEntity.ok(userServiceService.getServiceById(id));
    }

    // 3. Поиск по названию
    @GetMapping("/search")
    public ResponseEntity<List<ServiceResponseDTO>> searchServices(@RequestParam String query) {
        return ResponseEntity.ok(userServiceService.searchServices(query));
    }

    // 4. Фильтрация по цене
    @GetMapping("/filter")
    public ResponseEntity<List<ServiceResponseDTO>> filterServices(@RequestParam BigDecimal maxPrice) {
        return ResponseEntity.ok(userServiceService.filterByMaxPrice(maxPrice));
    }
}