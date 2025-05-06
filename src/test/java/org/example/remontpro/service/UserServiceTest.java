package org.example.remontpro.service;

import org.example.remontpro.models.Role;
import org.example.remontpro.models.User;
import org.example.remontpro.repositories.UserRepository;
import org.example.remontpro.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private UserRepository userRepository;

    @InjectMocks private UserService userService;

    @Test
    void loadUserByUsername_whenUserExists_returnsUserDetails() {
        User user = new User();
        user.setUsername("john");
        user.setPassword("pass");
        user.setRole(Role.USER);

        when(userRepository.findUserByUsername("john")).thenReturn(Optional.of(user));

        assertEquals("john", userService.loadUserByUsername("john").getUsername());
    }

    @Test
    void loadUserByUsername_whenUserNotFound_throwsException() {
        when(userRepository.findUserByUsername("missing")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("missing"));
    }
}
