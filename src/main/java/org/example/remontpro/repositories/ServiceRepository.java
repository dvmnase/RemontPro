package org.example.remontpro.repositories;

import org.example.remontpro.entities.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    // Поиск по названию (независимо от регистра)
    List<ServiceEntity> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String namePart, String descriptionPart);

    // Фильтрация по максимальной цене
    List<ServiceEntity> findByPriceLessThanEqual(BigDecimal maxPrice);
}
