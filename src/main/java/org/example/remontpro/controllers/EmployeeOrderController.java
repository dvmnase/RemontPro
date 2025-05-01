package org.example.remontpro.controllers;

import org.example.remontpro.entities.Employee;
import org.example.remontpro.entities.Order;
import org.example.remontpro.entities.OrderFile;
import org.example.remontpro.models.OrderStatus;
import org.example.remontpro.models.User;
import org.example.remontpro.repositories.EmployeeRepository;
import org.example.remontpro.repositories.OrderFileRepository;
import org.example.remontpro.repositories.OrderRepository;
import org.example.remontpro.repositories.UserRepository;
import org.example.remontpro.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmployeeOrderController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private OrderFileRepository orderFileRepository;

    // Получение всех заказов, закрепленных за сотрудником
    @GetMapping("/orders/{employeeId}")
    public ResponseEntity<List<Order>> getOrdersByEmployeeId(@PathVariable Long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        List<Order> orders = orderRepository.findByEmployeeId(employee.get().getId());
        return ResponseEntity.ok(orders);
    }


    // Обновление статуса заказа
    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId, @RequestParam String status, Authentication authentication) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isEmpty()) return ResponseEntity.notFound().build();

        Order order = orderOpt.get();
        if (!isOrderOwnedByAuthenticatedEmployee(order, authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        try {
            order.setStatus(OrderStatus.valueOf(status));
            orderRepository.save(order);
            return ResponseEntity.ok("Status updated");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid status");
        }
    }

    // Загрузка фото к заказу
    @PostMapping("/orders/{orderId}/files")
    public ResponseEntity<?> uploadOrderFile(@PathVariable Long orderId,
                                             @RequestParam("file") MultipartFile file,
                                             Authentication authentication) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }

        Order order = orderOpt.get();
        if (!isOrderOwnedByAuthenticatedEmployee(order, authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }

        try {
            OrderFile orderFile = new OrderFile();
            orderFile.setOrder(order);
            orderFile.setFileData(file.getBytes());  // <-- сохраняем файл как binary
            orderFileRepository.save(orderFile);

            return ResponseEntity.ok("File uploaded to DB as binary");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to read file: " + e.getMessage());
        }
    }


    private boolean isOrderOwnedByAuthenticatedEmployee(Order order, Authentication authentication) {
        if (order.getEmployee() == null) {
            System.out.println("Order " + order.getId() + " has no assigned employee.");
            return false;
        }

        String username = authentication.getName();
        Optional<User> user = userRepository.findUserByUsername(username);
        if (user.isEmpty()) {
            System.out.println("User " + username + " not found.");
            return false;
        }

        Optional<Employee> employee = employeeRepository.findByUserId(user.get().getId());
        if (employee.isEmpty()) {
            System.out.println("No employee bound to user " + username);
            return false;
        }

        boolean match = employee.get().getId().equals(order.getEmployee().getId());
        System.out.println("Authenticated employee: " + employee.get().getId()
                + ", Order employee: " + order.getEmployee().getId()
                + ", Match: " + match);

        return match;
    }


}
