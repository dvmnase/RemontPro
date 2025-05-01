// UserOrderController.java
package org.example.remontpro.controllers;

import lombok.RequiredArgsConstructor;
import org.example.remontpro.dto.EmployeeDto;
import org.example.remontpro.dto.OrderDetailsDto;
import org.example.remontpro.dto.OrderFileDto;
import org.example.remontpro.entities.Employee;
import org.example.remontpro.entities.Order;
import org.example.remontpro.requests.CreateOrderRequest;
import org.example.remontpro.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/orders")

public class UserOrderController {

    private final OrderService orderService;

    public UserOrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    // 📜 История заказов
    @GetMapping("/history/{clientId}")
    public ResponseEntity<List<OrderDetailsDto>> getOrderHistory(@PathVariable Long clientId) {
        return ResponseEntity.ok(orderService.getOrderHistory(clientId));
    }

    // 🔍 Отслеживание
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailsDto> getOrderDetails(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderDetails(orderId));
    }

    // 🔧 Получение сотрудников
    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDto>> getEmployees() {
        return ResponseEntity.ok(orderService.getAllEmployeesShort());
    }

    // 📝 Создание заказа
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    // ❌ Отмена заказа
    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId,
                                            @RequestParam Long clientId) {
        orderService.cancelOrder(orderId, clientId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{orderId}/files")
    public ResponseEntity<List<OrderFileDto>> getOrderFiles(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderFilesByOrderId(orderId));
    }

}
