
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.usermanagement.model.UserAccount;
import org.usermanagement.model.UserRole;

public class UserAccountTest {
    private UserAccount userAccount;

    @BeforeEach
    void setUp() {
        userAccount = new UserAccount("U1001", "John Doe", "john.doe@example.com", "password123", "1234567890", UserRole.STUDENT, "Inactive");
    }

    @Test
    void testActivateAccount() {
        // Ensure the initial status is Inactive
        assertEquals("Inactive", userAccount.getUserStatus());

        // Activate the account
        userAccount.activateAccount(userAccount);

        // Verify the account is now Active
        assertEquals("Active", userAccount.getUserStatus());
    }

    @Test
    void testDeactivateAccount() {
        // First activate the account to ensure it starts from Active state
        userAccount.activateAccount(userAccount);
        assertEquals("Active", userAccount.getUserStatus());

        // Deactivate the account
        userAccount.deactivateAccount(userAccount);

        // Verify the account is now Inactive
        assertEquals("Inactive", userAccount.getUserStatus());
    }
}
