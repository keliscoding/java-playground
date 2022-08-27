package io.github.zam0k.api.resources.exceptions;

import io.github.zam0k.api.services.exceptions.DataIntegrityViolationException;
import io.github.zam0k.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@SpringBootTest
class ResourceExceptionHandlerTest {

    public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado.";
    public static final String EMAIL_JA_CADASTRADO = "Email já cadastrado";
    @InjectMocks
    private ResourceExceptionHandler handler;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void whenObjectNotFoundExceptionThenReturnAResponseEntity() {
        ResponseEntity<StandardError> response = handler
                .objectNotFound(new ObjectNotFoundException(), new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(OBJETO_NAO_ENCONTRADO, response.getBody().getError());
        assertEquals(404, response.getBody().getStatus());
        assertNotEquals("/user/2", response.getBody().getPath());

        // teste que falha e não falha alternadamente, má prática? v
        //assertNotEquals(LocalDateTime.now(), response.getBody().getTimestamp());

        //System.out.println(LocalDateTime.now() + "  " + response.getBody().getTimestamp());
    }

    @Test
    void dataIntegrityViolation() {
        ResponseEntity<StandardError> response = handler
                .dataIntegrityViolation(
                        new DataIntegrityViolationException(EMAIL_JA_CADASTRADO),
                        new MockHttpServletRequest()
                );
        
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(EMAIL_JA_CADASTRADO, response.getBody().getError());
        assertEquals(400, response.getBody().getStatus());
    }
}