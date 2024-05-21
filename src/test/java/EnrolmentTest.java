import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.usermanagement.model.Enrolment;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;

public class EnrolmentTest {
    private Enrolment enrolment;
    private final InputStream systemIn = System.in;
    private ByteArrayInputStream testIn;

    @Before
    public void setUp() {
        enrolment = new Enrolment("E001", "C101", "U1001", new Date());
    }

    @After
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
    }

    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @Test
    public void testEnrolmentCreation() {
        assertNotNull(enrolment);
        assertEquals("E001", enrolment.getEnrolmentId());
        assertEquals("C101", enrolment.getCourseId());
        assertEquals("U1001", enrolment.getUserId());
    }

    @Test
    public void testEnrol() {
        provideInput("C102\n");
        enrolment.enrol();
        assertEquals("C102", enrolment.getCourseId());
    }

    @Test
    public void testDrop() {
        provideInput("C101\n");
        enrolment.drop();
        assertEquals("C101", enrolment.getCourseId());
    }

    @Test
    public void testManage() {
        provideInput("C101\n");
        enrolment.manage();
        assertNotNull(enrolment);
    }
}
