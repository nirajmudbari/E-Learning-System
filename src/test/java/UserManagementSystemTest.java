import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.usermanagement.model.UserManagementSystem;

import java.io.*;

import static org.junit.Assert.*;

//	Try to cover unit tests for all the other features inside UserManagementSystem.

public class UserManagementSystemTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    UserManagementSystem userManagementSystem;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Test sign-up with valid input")
    public void testSignUpWithValidInput() throws IOException {
        // Mock user input
        String userInput = "John Doe\njohn@example.com\npassword123\n1234567890\n1\n";
        InputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);

        // Call signUp method
        userManagementSystem = UserManagementSystem.getInstance();
        userManagementSystem.signUp();

        // Check if the output contains success message
        assertTrue(outContent.toString().contains("User signed up successfully!"));
    }

    @Test
    @DisplayName("Test login with valid credentials")
    public void testLoginWithValidCredentials() throws IOException {
        // Mock user sign-up
        String signUpInput = "Jane Doe\njane@example.com\npassword456\n0987654321\n2\n";
        InputStream signUpIn = new ByteArrayInputStream(signUpInput.getBytes());
        System.setIn(signUpIn);
        userManagementSystem = UserManagementSystem.getInstance();

        userManagementSystem.signUp();

        // Mock user login input
        String loginInput = "jane@example.com\npassword456\n";
        InputStream loginIn = new ByteArrayInputStream(loginInput.getBytes());
        System.setIn(loginIn);

        // Call login method
        assertTrue(userManagementSystem.login());
    }

    @Test
    @DisplayName("Test logout when user is logged in")
    public void testLogoutWhenUserIsLoggedIn() throws IOException {
        // Mock user sign-up
        String signUpInput = "Charlie Green\ncharlie@example.com\npassword111\n4455667788\n1\n";
        InputStream signUpIn = new ByteArrayInputStream(signUpInput.getBytes());
        System.setIn(signUpIn);
        userManagementSystem = UserManagementSystem.getInstance();

        userManagementSystem.signUp();

        // Mock user login input
        String loginInput = "charlie@example.com\npassword111\n";
        InputStream loginIn = new ByteArrayInputStream(loginInput.getBytes());
        System.setIn(loginIn);
        userManagementSystem.login();

        // Call logout method
        userManagementSystem.logout();

        // Check if the output contains logout message
        assertTrue(outContent.toString().contains("Logged out successfully!"));
    }
}
