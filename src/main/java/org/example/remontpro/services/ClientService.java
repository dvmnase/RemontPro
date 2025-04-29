package org.example.remontpro.services;

import org.example.remontpro.entities.Client;
import org.example.remontpro.exceptions.ResourceNotFoundException;
import org.example.remontpro.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAllByOrderByIdAsc();
    }

    public Client blockClient(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with ID: " + clientId));
        client.setBlocked(true);
        return clientRepository.save(client);
    }
    public Client unblockUser(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with ID: " + clientId));
        client.setBlocked(false);
        return clientRepository.save(client);
    }
}
