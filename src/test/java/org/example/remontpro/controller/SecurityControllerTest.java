package org.example.remontpro.controller;

import org.example.remontpro.JwtCore;
import org.example.remontpro.controllers.SecurityController;
import org.example.remontpro.models.User;
import org.example.remontpro.repositories.UserRepository;
import org.example.remontpro.requests.SignupRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SecurityControllerTest {

    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private AuthenticationManager authenticationManager;
    @Mock private JwtCore jwtCore;

    @InjectMocks private SecurityController securityController;

    @Test
    void signup_whenUsernameExists_returnsBadRequest() {
        SignupRequest request = new SignupRequest();
        request.setUsername("test");

        when(userRepository.existsByUsername("test")).thenReturn(true);

        ResponseEntity<?> response = securityController.signup(request);
        assertEquals(400, response.getStatusCodeValue());
    }


}
