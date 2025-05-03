package org.example.remontpro.services;

import org.example.remontpro.dto.ServiceRequestDTO;
import org.example.remontpro.dto.ServiceResponseDTO;
import org.example.remontpro.entities.ServiceEntity;
import org.example.remontpro.exceptions.ResourceNotFoundException;
import org.example.remontpro.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;

    @Autowired
    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    private ServiceResponseDTO convertToDto(ServiceEntity service) {
        ServiceResponseDTO dto = new ServiceResponseDTO();
        dto.setId(service.getId());
        dto.setName(service.getName());
        dto.setDescription(service.getDescription());
        dto.setPrice(service.getPrice());
        dto.setPhoto(service.getPhoto());
        return dto;
    }

    private ServiceEntity convertToEntity(ServiceRequestDTO dto) throws IOException {
        ServiceEntity service = new ServiceEntity();
        service.setName(dto.getName());
        service.setDescription(dto.getDescription());
        service.setPrice(dto.getPrice());
        if (dto.getPhoto() != null && !dto.getPhoto().isEmpty()) {
            service.setPhoto(dto.getPhoto().getBytes());
        }
        return service;
    }

    public List<ServiceResponseDTO> getAllServices() {
        return serviceRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ServiceResponseDTO getServiceById(Long id) {
        ServiceEntity service = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + id));
        return convertToDto(service);
    }

    public ServiceResponseDTO createService(ServiceRequestDTO dto) throws IOException {
        ServiceEntity service = convertToEntity(dto);
        ServiceEntity savedService = serviceRepository.save(service);
        return convertToDto(savedService);
    }

    public ServiceResponseDTO updateService(Long id, ServiceRequestDTO dto) throws IOException {
        ServiceEntity existingService = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + id));

        existingService.setName(dto.getName());
        existingService.setDescription(dto.getDescription());
        existingService.setPrice(dto.getPrice());
        if (dto.getPhoto() != null && !dto.getPhoto().isEmpty()) {
            existingService.setPhoto(dto.getPhoto().getBytes());
        }

        ServiceEntity updatedService = serviceRepository.save(existingService);
        return convertToDto(updatedService);
    }

    public void deleteService(Long id) {
        ServiceEntity existingService = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + id));
        serviceRepository.delete(existingService);
    }

    public List<ServiceResponseDTO> searchServices(String query) {
        return serviceRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ServiceResponseDTO> filterByMaxPrice(BigDecimal maxPrice) {
        return serviceRepository.findByPriceLessThanEqual(maxPrice)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}