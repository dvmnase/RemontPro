package org.example.remontpro.controllers;

import org.example.remontpro.dto.EmployeeRequestDTO;
import org.example.remontpro.dto.EmployeeResponseDTO;
import org.example.remontpro.exceptions.ResourceNotFoundException;
import org.example.remontpro.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("/secured/admin/employees")
@Secured("ROLE_ADMIN")
public class AdminEmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(
            @RequestPart("employee") EmployeeRequestDTO dto,
            @RequestPart(value = "photo", required = false) MultipartFile photo) throws IOException {
        if (photo != null) {
            dto.setPhoto(photo);
        }
        return ResponseEntity.ok(employeeService.createEmployee(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(
            @PathVariable Long id,
            @RequestPart("employee") EmployeeRequestDTO dto,
            @RequestPart(value = "photo", required = false) MultipartFile photo) throws IOException {
        if (photo != null) {
            dto.setPhoto(photo);
        }
        return ResponseEntity.ok(employeeService.updateEmployee(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException ex) {
            throw ex;
        }
    }
}