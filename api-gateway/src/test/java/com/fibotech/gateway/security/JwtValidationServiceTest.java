package com.fibotech.gateway.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class JwtValidationServiceTest {

    private static final String TEST_SECURITY_KEY = "50655368566B5970337336763979244226452948404D635166546A576E5A7134";

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private JwtValidationService jwtValidationService;

    @BeforeEach
    public void setup() throws IllegalAccessException {
        MockitoAnnotations.openMocks(this);

        var fields = jwtValidationService.getClass().getDeclaredFields();

        for (var field : fields){
            if (field.getName().equals("EXP_MINUTES")){
                field.setAccessible(true);
                field.set(jwtValidationService,5);
            }
            if (field.getName().equals("SECURITY_KEY")) {
                field.setAccessible(true);
                field.set(jwtValidationService, TEST_SECURITY_KEY);
            }
            if (field.getName().equals("temporalUnit")) {
                field.setAccessible(true);
                field.set(jwtValidationService, ChronoUnit.SECONDS);
            }
        }
    }

    @Test
    public void testGenerateAndValidateToken() {
        when(userDetails.getUsername()).thenReturn("test-user");
        String token = jwtValidationService.generate(userDetails);

        assertNotNull(token);
        assertTrue(jwtValidationService.validateToken(token));
        assertFalse(jwtValidationService.isTokenExpired(token));
        assertEquals("test-user", jwtValidationService.extractUserName(token));
        assertTrue(jwtValidationService.isTokenValid(token, userDetails));
    }

    @Test
    public void testExpiredToken() throws InterruptedException {
        when(userDetails.getUsername()).thenReturn("test-user");
        String token = jwtValidationService.generate(userDetails);

        Thread.sleep(2 * 1000);
        assertFalse(jwtValidationService.isTokenExpired(token));
        Thread.sleep(4 * 1000);
        assertThrows(ExpiredJwtException.class,()->jwtValidationService.isTokenExpired(token));
    }

    @Test
    public void TestValidateToken() {
        when(userDetails.getUsername()).thenReturn("test-user");
        String token = jwtValidationService.generate(userDetails);

        assertTrue(jwtValidationService.validateToken(token));
    }

    @Test
    public void TestNotValidateToken() {
        String token = "invalidtoken";

        assertFalse(jwtValidationService.validateToken(token));
    }


    @Test
    public void testIsTokenValid(){
        when(userDetails.getUsername()).thenReturn("test-user");
        String token = jwtValidationService.generate(userDetails);

        assertEquals("test-user", jwtValidationService.extractUserName(token));
        assertTrue(jwtValidationService.isTokenValid(token,userDetails));
    }

    @Test
    public void testExtractUserNameInvalidToken() {
        String token = "invalidtoken";

        assertThrows(MalformedJwtException.class, () -> {
            jwtValidationService.extractUserName(token);
        });
    }
}
