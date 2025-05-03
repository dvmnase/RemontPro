package org.example.remontpro.controllers;

import org.example.remontpro.dto.ServiceRequestDTO;
import org.example.remontpro.dto.ServiceResponseDTO;
import org.example.remontpro.exceptions.ResourceNotFoundException;
import org.example.remontpro.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/secured/admin/services")
public class AdminServiceController {

    private final ServiceService serviceService;

    @Autowired
    public AdminServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping
    public List<ServiceResponseDTO> getAllServices() {
        return serviceService.getAllServices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponseDTO> getServiceById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceService.getServiceById(id));
    }

    @PostMapping
    public ResponseEntity<ServiceResponseDTO> createService(
            @RequestPart("service") ServiceRequestDTO dto,
            @RequestPart(value = "photo", required = false) MultipartFile photo) throws IOException {
        if (photo != null) {
            dto.setPhoto(photo);
        }
        return ResponseEntity.ok(serviceService.createService(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponseDTO> updateService(
            @PathVariable Long id,
            @RequestPart("service") ServiceRequestDTO dto,
            @RequestPart(value = "photo", required = false) MultipartFile photo) throws IOException {
        if (photo != null) {
            dto.setPhoto(photo);
        }
        return ResponseEntity.ok(serviceService.updateService(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        serviceService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}