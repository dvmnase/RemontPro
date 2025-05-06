package org.example.remontpro;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class JwtCoreTest {

    private JwtCore jwtCore;

    @BeforeEach
    void setUp() {
        jwtCore = new JwtCore("mysecretkeymysecretkey1234567890!!", 7200000); // 32+ символа
    }


    @Test
    void generateToken_and_extractUsername() {
        UserDetailsImpl userDetails = new UserDetailsImpl(1L, "john", "john@mail.com", "pass",
                true, Collections.singletonList((GrantedAuthority) () -> "ROLE_USER"));

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        String token = jwtCore.generateToken(auth);
        assertNotNull(token);

        String username = jwtCore.getNameFromJwt(token);
        assertEquals("john", username);
    }

    @Test
    void validateToken_withInvalidToken_returnsFalse() {
        String invalidToken = "invalid.token.here";
        assertFalse(jwtCore.validateToken(invalidToken));
    }
}
