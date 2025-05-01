package org.example.remontpro.repositories;

import org.example.remontpro.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findAllByOrderByIdAsc();
    Optional<Client> findByUserId(Long userId);
}

