import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.usermanagement.model.Grade;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class GradeTest {
    private Grade grade;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    void setUp() {
        grade = new Grade("001", "U1001", "C1001", "A");
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testGradeCreation() {
        assertNotNull(grade);
        assertEquals("001", grade.getGradeId());
        assertEquals("U1001", grade.getUserId());
        assertEquals("C1001", grade.getCourseId());
        assertEquals("A", grade.getGrade());
    }
}
