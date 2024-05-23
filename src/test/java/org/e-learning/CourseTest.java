package org.coursemanagement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public class CourseTest {

    private Course course;

    @Before
    public void setUp() {
        course = new Course("Math", "Mathematics course", "Passing Math 101");
    }

    @Test
    public void testAddNewCourse() {
        Assert.assertEquals("Math", course.getTitle());
        Assert.assertEquals("Mathematics course", course.getContent());
        Assert.assertEquals("Passing Math 101", course.getCriteria());
    }

    @Test
    public void testEnrollNewStudentToCourse_NormalOperation() {
        Student student = new Student("S001", "John Doe");
        course.enrollStudent(student);
        Assert.assertTrue(course.getStudents().contains(student));
    }

//    @Test
//    public void testEnrollNewStudentToCourse_DuplicateEnrollment() {
//        Student student = new Student("S001", "John Doe");
//        course.enrollStudent(student);
//
//        try {
//            course.enrollStudent(student);
//            Assert.fail("Expected IllegalArgumentException was not thrown");
//        } catch (IllegalArgumentException e) {
//            Assert.assertNotNull(e.getMessage());
//            Assert.assertEquals("Student is already enrolled in this course.", e.getMessage());
//        }
//    }

    @After
    public void tearDown() {
        course = null;
    }
}
