package org.usermanagement.model;

import java.util.Date;
import java.util.Scanner;

public class Enrolment implements UserActions {
    private String enrolmentId;
    private String courseId;
    private String userId;
    private Date enrolmentDate;


    public Enrolment(String enrolmentId, String courseId, String userId, Date enrolmentDate) {
        this.enrolmentId = enrolmentId;
        this.courseId = courseId;
        this.userId = userId;
        this.enrolmentDate = enrolmentDate;
    }

    public String getEnrolmentId() {
        return enrolmentId;
    }

    public void setEnrolmentId(String enrolmentId) {
        this.enrolmentId = enrolmentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getEnrolmentDate() {
        return enrolmentDate;
    }

    public void setEnrolmentDate(Date enrolmentDate) {
        this.enrolmentDate = enrolmentDate;
    }

    @Override
    public void enrol() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter course ID to enroll: ");
        this.courseId = scanner.nextLine();
        System.out.println("Enrolling user: " + userId + " in course: " + courseId);
        System.out.println("Enrolment ID: " + enrolmentId + ", Date: " + enrolmentDate);
        System.out.println("Enrollment for :" + courseId + " completed successfully, you can see your enrolled course in manage enrollment.");
    }

    @Override
    public void drop() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter course ID to drop: ");
        this.courseId = scanner.nextLine();
        System.out.println("Dropping user: " + userId + " from course: " + courseId);
        System.out.println("Successfully dropped the course: " + courseId);

    }

    @Override
    public void manage() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Managing enrolment for user: " + userId);
        System.out.println("Enrolment ID: " + enrolmentId + ", Date: " + enrolmentDate);
        System.out.println("Courses enrolled in:");
        System.out.println("1. Course ITECH7201");
        System.out.println("2. Course ITECH5500");
        System.out.println("3. Course ITECH7400");
        System.out.print("Enter course ID to view details: ");
        String courseId = scanner.nextLine();
        System.out.println("Viewing details for course ID: " + courseId);
        // Simulate showing more details about the selected course
    }

}
