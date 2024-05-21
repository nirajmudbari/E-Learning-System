
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.usermanagement.model.Enrolment;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;

public class EnrolmentTest {
    private Enrolment enrolment;

    @BeforeEach
    void setUp() {
        enrolment = new Enrolment("E001", "C101", "U1001", new Date());
    }

    @Test
    void testEnrolmentCreation() {
        assertNotNull(enrolment);
        assertEquals("E001", enrolment.getEnrolmentId());
        assertEquals("C101", enrolment.getCourseId());
        assertEquals("U1001", enrolment.getUserId());
    }

    @Test
    void testEnrol() {
        String input = "C102\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        enrolment.enrol();
        assertEquals("C102", enrolment.getCourseId());
    }

    @Test
    void testDrop() {
        String input = "C101\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        enrolment.drop();
        assertEquals("C101", enrolment.getCourseId());
    }

    @Test
    void testManage() {
        String input = "C101\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        enrolment.manage();
        assertNotNull(enrolment);
    }
}
