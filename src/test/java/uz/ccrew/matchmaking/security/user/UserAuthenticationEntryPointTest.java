package uz.ccrew.matchmaking.security.user;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class UserAuthenticationEntryPointTest {
    @InjectMocks
    private UserAuthenticationEntryPoint entryPoint;

    @Test
    void testCommence() throws IOException {
        MockitoAnnotations.openMocks(this);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        AuthenticationException authException = new AuthenticationException("Unauthorized") {
        };

        entryPoint.commence(request, response, authException);

        assertEquals("application/json", response.getContentType());
        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus());
        assertEquals("{ \"errors\": [\"Unauthorized\"] }", response.getContentAsString());
    }
}