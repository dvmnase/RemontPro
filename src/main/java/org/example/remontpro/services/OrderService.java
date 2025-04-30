// OrderService.java
package org.example.remontpro.services;

import lombok.RequiredArgsConstructor;
import org.example.remontpro.dto.EmployeeDto;
import org.example.remontpro.dto.OrderDetailsDto;
import org.example.remontpro.entities.Employee;
import org.example.remontpro.entities.Order;
import org.example.remontpro.entities.ServiceEntity;
import org.example.remontpro.exceptions.BadRequestException;
import org.example.remontpro.exceptions.ResourceNotFoundException;
import org.example.remontpro.models.OrderStatus;
import org.example.remontpro.repositories.ClientRepository;
import org.example.remontpro.repositories.EmployeeRepository;
import org.example.remontpro.repositories.OrderRepository;
import org.example.remontpro.repositories.ServiceRepository;
import org.example.remontpro.requests.CreateOrderRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final EmployeeRepository employeeRepository;
    private final ServiceRepository serviceRepository;
    private final ClientRepository clientRepository;

    public OrderService(OrderRepository orderRepository, EmployeeRepository employeeRepository,ServiceRepository serviceRepository, ClientRepository clientRepository) {
        this.orderRepository = orderRepository;
        this.employeeRepository = employeeRepository;
        this.serviceRepository = serviceRepository;
        this.clientRepository = clientRepository;
    }

    public List<OrderDetailsDto> getOrderHistory(Long clientId) {
        return orderRepository.findByClientId(clientId).stream()
                .map(this::mapToOrderDetails)
                .collect(Collectors.toList());
    }

    public OrderDetailsDto getOrderDetails(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return mapToOrderDetails(order);
    }

    public Order createOrder(CreateOrderRequest request) {
        Order order = new Order();
        order.setClientId(request.getClientId());
        order.setServiceId(request.getServiceId());
        order.setEmployee(request.getEmployeeId() != null
                ? employeeRepository.findById(request.getEmployeeId()).orElse(null)
                : null);
        order.setStatus(OrderStatus.NEW);
        order.setDescription(request.getDescription());
        return orderRepository.save(order);
    }

    public void cancelOrder(Long orderId, Long clientId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Заказ не найден"));

        if (!order.getClientId().equals(clientId)) {
            throw new BadRequestException("Доступ запрещён: заказ принадлежит другому клиенту");
        }

        if (order.getStatus() != OrderStatus.NEW) {
            throw new BadRequestException("Невозможно отменить заказ. Он уже в процессе или завершён.");
        }

        orderRepository.delete(order);
    }


    private OrderDetailsDto mapToOrderDetails(Order order) {
        OrderDetailsDto dto = new OrderDetailsDto();
        dto.setOrderId(order.getId());
        ServiceEntity service = serviceRepository.findById(order.getServiceId()).orElse(null);
        dto.setServiceName(service != null ? service.getName() : "Unknown");
        dto.setServicePrice(service != null ? service.getPrice() : null);
        dto.setServiceId(service.getId());

        Employee employee = order.getEmployee();
        dto.setEmployeeName(employee != null ? employee.getFullName() : "Not assigned");
        dto.setEmployeePhone(employee != null ? employee.getPhoneNumber() : null);
        dto.setEmployeeId(employee != null ? employee.getId() : null);

        dto.setStatus(order.getStatus());
        dto.setDescription(order.getDescription());
        dto.setCreatedAt(order.getCreatedAt());
        return dto;
    }

    public List<EmployeeDto> getAllEmployeesShort() {
        return employeeRepository.findAll().stream().map(emp -> {
            EmployeeDto dto = new EmployeeDto();
            dto.setId(emp.getId());
            dto.setFullName(emp.getFullName());
            dto.setPhoneNumber(emp.getPhoneNumber());
            dto.setQualification(emp.getQualification());
            return dto;
        }).toList();
    }
}
