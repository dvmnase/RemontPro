package org.example.remontpro.repositories;

import org.example.remontpro.entities.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    // Можно добавить свои запросы, если будут нужны
}
