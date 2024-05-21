
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.usermanagement.model.Payment;
import org.usermanagement.model.UserAccount;
import org.usermanagement.model.UserRole;

import java.util.Date;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class PaymentTest {

    private Payment payment;
    private UserAccount userAccount;

    @BeforeEach
    void setUp() {
        payment = new Payment("P001", 100.0f, new Date());
        userAccount = new UserAccount("U001", "John Doe", "john@example.com", "password123", "555-1234", UserRole.STUDENT, "Active");
    }

    @Test
    void testFullPayment() {
        String input = "500.0\n"; // Full payment equal to initial balance
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        payment.pay(userAccount);

        assertEquals(0, userAccount.getBalance(), "User's balance should be 0 after full payment.");
    }

    @Test
    void testPartialPayment() {
        String input = "250.0\n"; // Partial payment
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        payment.pay(userAccount);

        assertEquals(250.0f, userAccount.getBalance(), "User's balance should be 250 after partial payment.");
    }

    @Test
    void testViewPayment() {
        payment.viewPayment();
        // Assuming viewPayment() just prints, we can't easily test it without capturing stdout.
        // We assume it's correct if no exceptions are thrown.
    }
}
