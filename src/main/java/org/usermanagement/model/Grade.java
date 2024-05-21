package org.usermanagement.model;

import java.util.Scanner;

public class Grade {
    private String gradeId;
    private String userId;
    private String courseId;
    private String grade;

    public Grade(String gradeId, String userId, String courseId, String grade) {
        this.gradeId = gradeId;
        this.userId = userId;
        this.courseId = courseId;
        this.grade = grade;
    }

    public Grade() {
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void getGradeByUser() {
        System.out.println("Getting grade for user: " + userId);
        System.out.println("Grade ID: " + gradeId + ", Course ID: " + courseId + ", Grade: " + grade);
    }

    public void getGradeByCourse() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter course ID to get grades: ");
        String courseId = scanner.nextLine();
        System.out.println("Getting grades for course: " + courseId);
        System.out.println("Grade ID: 001, User ID: U1001, Grade: A");
        System.out.println("Grade ID: 002, User ID: U1002, Grade: B+");
        System.out.println("Grade ID: 003, User ID: U1003, Grade: A-");
        System.out.println("Grade ID: 004, User ID: U1004, Grade: B");
    }
}
