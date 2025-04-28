package org.example.remontpro.controllers;

import org.example.remontpro.entities.ServiceEntity;
import org.example.remontpro.exceptions.ResourceNotFoundException;
import org.example.remontpro.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/secured/admin/services")
public class AdminServiceController {

    private final ServiceService serviceService;

    @Autowired
    public AdminServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    // Получить все услуги
    @GetMapping
    public List<ServiceEntity> getAllServices() {
        return serviceService.getAllServices();
    }

    // Получить услугу по ID
    @GetMapping("/{id}")
    public ResponseEntity<ServiceEntity> getServiceById(@PathVariable Long id) {
        ServiceEntity service = serviceService.getServiceById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + id));
        return ResponseEntity.ok(service);
    }


    // Создать новую услугу
    @PostMapping
    public ServiceEntity createService(@RequestBody ServiceEntity serviceEntity) {
        return serviceService.createService(serviceEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceEntity> updateService(@PathVariable Long id, @RequestBody ServiceEntity serviceEntity) {
        return ResponseEntity.ok(serviceService.updateService(id, serviceEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        serviceService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
