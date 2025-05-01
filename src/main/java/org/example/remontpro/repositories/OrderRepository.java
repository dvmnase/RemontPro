package org.example.remontpro.repositories;

import org.example.remontpro.entities.Order;
import org.example.remontpro.models.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Long countByEmployeeIdAndStatus(Long employeeId, OrderStatus status);
    Long countByStatus(OrderStatus status);
    List<Order> findByClientId(Long clientId);
    List<Order> findByEmployeeId(Long employeeId);



}
