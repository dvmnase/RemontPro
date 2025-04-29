package org.example.remontpro.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.remontpro.dto.EmployeeOrderStatsDto;
import org.example.remontpro.entities.Employee;
import org.example.remontpro.models.OrderStatus;
import org.example.remontpro.models.User;
import org.example.remontpro.repositories.EmployeeRepository;
import org.example.remontpro.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminStatsService {

    private final EmployeeRepository employeeRepository;
    private final OrderRepository orderRepository;

    public AdminStatsService(EmployeeRepository employeeRepository, OrderRepository orderRepository) {
        this.employeeRepository = employeeRepository;
        this.orderRepository = orderRepository;
    }

    public List<EmployeeOrderStatsDto> getAllEmployeeStats() {
        List<Employee> employees = employeeRepository.findAll();

        return employees.stream().map(employee -> {
            User user = employee.getUser();
            return new EmployeeOrderStatsDto(
                    employee.getId(),
                    employee.getFullName(),
                    employee.getQualification(),
                    employee.getPhoneNumber(),
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    orderRepository.countByEmployeeIdAndStatus(employee.getId(), OrderStatus.NEW),
                    orderRepository.countByEmployeeIdAndStatus(employee.getId(), OrderStatus.IN_PROGRESS),
                    orderRepository.countByEmployeeIdAndStatus(employee.getId(), OrderStatus.COMPLETED),
                    orderRepository.countByEmployeeIdAndStatus(employee.getId(), OrderStatus.CANCELLED)
            );
        }).collect(Collectors.toList());
    }

    public EmployeeOrderStatsDto getStatsForEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        User user = employee.getUser();

        return new EmployeeOrderStatsDto(
                employee.getId(),
                employee.getFullName(),
                employee.getQualification(),
                employee.getPhoneNumber(),
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                orderRepository.countByEmployeeIdAndStatus(employee.getId(), OrderStatus.NEW),
                orderRepository.countByEmployeeIdAndStatus(employee.getId(), OrderStatus.IN_PROGRESS),
                orderRepository.countByEmployeeIdAndStatus(employee.getId(), OrderStatus.COMPLETED),
                orderRepository.countByEmployeeIdAndStatus(employee.getId(), OrderStatus.CANCELLED)
        );
    }

    public Map<String, Long> getOrderCountsByStatus() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("NEW", orderRepository.countByStatus(OrderStatus.NEW));
        stats.put("IN_PROGRESS", orderRepository.countByStatus(OrderStatus.IN_PROGRESS));
        stats.put("COMPLETED", orderRepository.countByStatus(OrderStatus.COMPLETED));
        stats.put("CANCELLED", orderRepository.countByStatus(OrderStatus.CANCELLED));
        return stats;
    }
}
