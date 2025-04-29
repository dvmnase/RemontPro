package org.example.remontpro.controllers;

import org.example.remontpro.entities.Client;
import org.example.remontpro.models.User;
import org.example.remontpro.services.ClientService;
import org.example.remontpro.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/secured/admin/users")
@Secured("ROLE_ADMIN")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/clients")
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @PostMapping("/clients/{id}/block")
    public ResponseEntity<Client> blockClient(@PathVariable Long id) {
        Client blocked = clientService.blockClient(id);
        return ResponseEntity.ok(blocked);
    }
    @PostMapping("/clients/{id}/unblock")
    public ResponseEntity<Client> unblockClient(@PathVariable Long id) {
        Client unblocked = clientService.unblockUser(id);
        return ResponseEntity.ok(unblocked);
    }
}
