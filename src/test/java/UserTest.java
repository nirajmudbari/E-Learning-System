import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.usermanagement.model.User;
import org.usermanagement.model.UserRole;

public class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("U001", "John Doe", "john@example.com", "password123", "1234567890", UserRole.STUDENT);
    }

    @Test
    void testUserCreation() {
        assertNotNull(user);
        assertEquals("U001", user.getUserId());
        assertEquals("John Doe", user.getFullname());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals("1234567890", user.getPhone());
        assertEquals(UserRole.STUDENT, user.getRole());
    }

    @Test
    void testLoginSuccess() {
        assertTrue(user.login("john@example.com", "password123"));
    }

    @Test
    void testLoginFailureIncorrectPassword() {
        assertFalse(user.login("john@example.com", "wrongpassword"));
    }

    @Test
    void testLoginFailureIncorrectEmail() {
        assertFalse(user.login("wrong@example.com", "password123"));
    }

}
