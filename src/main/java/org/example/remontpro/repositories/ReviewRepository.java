package org.example.remontpro.repositories;

import org.example.remontpro.entities.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findByOrderId(Long orderId);
    List<ReviewEntity> findByClientId(Long clientId);
    Boolean existsByOrderId(Long orderId);
}
