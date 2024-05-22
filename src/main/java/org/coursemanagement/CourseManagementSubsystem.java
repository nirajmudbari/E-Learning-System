package org.coursemanagement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

interface User {
    void login(String username, String password);
}

class Admin implements User {
    private static Admin instance;
    private boolean loggedIn;

    private Admin() {
        this.loggedIn = false;
    }

    public static Admin getInstance() {
        if (instance == null) {
            instance = new Admin();
        }
        return instance;
    }

    @Override
    public void login(String username, String password) {
        if ("admin".equals(username) && "password".equals(password)) {
            loggedIn = true;
            System.out.println("Admin logged in.");
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
}

class Student implements User{
    private String name;
    private String id;
    private List<Assignment> assignments;

    public Student(String name, String id) {
        this.name = name;
        this.id = id;
        this.assignments = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public String getName() {
        return name;
    }

    @Override
    public void login(String username, String password) {
        // TODO Auto-generated method stub

    }
}

class Course {
    private String title;
    private String content;
    private String criteria;
    private List<Student> students;
    private List<Assignment> assignments;

    public Course(String title, String content, String criteria) {
        this.title = title;
        this.content = content;
        this.criteria = criteria;
        this.students = new ArrayList<>();
        this.assignments = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public void enrollStudent(Student student) {
        students.add(student);
    }

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public List<Student> getStudents() {
        return students;
    }

    public String getContent() {
        return content;
    }

    public String getCriteria() {
        return criteria;
    }

}

class Assignment {
    private String title;
    private String content;
    private LocalDate deadline;
    private AssignmentState state;

    public Assignment(String title, String content, LocalDate deadline) {
        this.title = title;
        this.content = content;
        this.deadline = deadline;
        this.state = new PendingState(this);
    }

    public void extendDeadline(LocalDate newDeadline) {
        this.deadline = newDeadline;
        System.out.println("Deadline extended.");
    }

    public void submit() {
        state.submit();
    }

    public void review() {
        state.review();
    }

    public void provideFeedback() {
        state.provideFeedback();
    }

    public void setState(AssignmentState state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getDeadline() {
        return deadline;
    }
}

interface AssignmentState {
    void submit();

    void review();

    void provideFeedback();
}

class PendingState implements AssignmentState {
    private Assignment assignment;

    public PendingState(Assignment assignment) {
        this.assignment = assignment;
    }

    @Override
    public void submit() {
        assignment.setState(new SubmittedState(assignment));
        System.out.println("Assignment submitted.");
    }

    @Override
    public void review() {
        System.out.println("Assignment not submitted yet.");
    }

    @Override
    public void provideFeedback() {
        System.out.println("Assignment not submitted yet.");
    }
}

class SubmittedState implements AssignmentState {
    private Assignment assignment;

    public SubmittedState(Assignment assignment) {
        this.assignment = assignment;
    }

    @Override
    public void submit() {
        System.out.println("Assignment already submitted.");
    }

    @Override
    public void review() {
        assignment.setState(new ReviewedState(assignment));
        System.out.println("Assignment reviewed.");
    }

    @Override
    public void provideFeedback() {
        System.out.println("Review the assignment before providing feedback.");
    }
}

class ReviewedState implements AssignmentState {
    private Assignment assignment;

    public ReviewedState(Assignment assignment) {
        this.assignment = assignment;
    }

    @Override
    public void submit() {
        System.out.println("Assignment already reviewed.");
    }

    @Override
    public void review() {
        System.out.println("Assignment already reviewed.");
    }

    @Override
    public void provideFeedback() {
        System.out.println("Feedback provided.");
    }
}

public class CourseManagementSubsystem
{
    private static Scanner scanner = new Scanner(System.in);
    private static List<Course> courses = new ArrayList<>();
    private static List<Student> students = new ArrayList<>();

    public CourseManagementSubsystem() {
    }

    //    public static void main(String[] args) {
//        Admin admin = Admin.getInstance();
//        boolean exit = false;
//
//        while (!exit) {
//            System.out.println("\n1. Admin Login");
//            System.out.println("2. Create Course");
//            System.out.println("3. Modify Course Details");
//            System.out.println("4. Enroll Student to Course");
//            System.out.println("5. Create Assignment");
//            System.out.println("6. View Assignments");
//            System.out.println("7. Extend Assignment Deadline");
//            System.out.println("8. Delete Assignment");
//            System.out.println("9. Track Student Progress");
//            System.out.println("10. Provide Feedback");
//            System.out.println("11. View All Courses");
//            System.out.println("12. View All Student Enrollments");
//            System.out.println("13. Exit");
//            System.out.print("Enter your choice: ");
//            int choice = scanner.nextInt();
//            scanner.nextLine();
//
//            switch (choice) {
//                case 1:
//                    adminLogin(admin);
//                    break;
//                case 2:
//                    if (adminLoggedIn) {
//                        createCourse();
//                    } else {
//                        System.out.println("Admin not logged in.");
//                    }
//                    break;
//                case 3:
//                    if (adminLoggedIn) {
//                        modifyCourse();
//                    } else {
//                        System.out.println("Admin not logged in.");
//                    }
//                    break;
//                case 4:
//                    if (adminLoggedIn) {
//                        enrollStudent();
//                    } else {
//                        System.out.println("Admin not logged in.");
//                    }
//                    break;
//                case 5:
//                    if (adminLoggedIn) {
//                        createAssignment();
//                    } else {
//                        System.out.println("Admin not logged in.");
//                    }
//                    break;
//                case 6:
//                    if (adminLoggedIn) {
//                        viewAssignments();
//                    } else {
//                        System.out.println("Admin not logged in.");
//                    }
//                    break;
//                case 7:
//                    if (adminLoggedIn) {
//                        extendDeadline();
//                    } else {
//                        System.out.println("Admin not logged in.");
//                    }
//                    break;
//                case 8:
//                    if (adminLoggedIn) {
//                        deleteAssignment();
//                    } else {
//                        System.out.println("Admin not logged in.");
//                    }
//                    break;
//                case 9:
//                    if (adminLoggedIn) {
//                        trackStudentProgress();
//                    } else {
//                        System.out.println("Admin not logged in.");
//                    }
//                    break;
//                case 10:
//                    if (adminLoggedIn) {
//                        provideFeedback();
//                    } else {
//                        System.out.println("Admin not logged in.");
//                    }
//                    break;
//                case 11:
//                    if (adminLoggedIn) {
//                        viewAllCourses();
//                    } else {
//                        System.out.println("Admin not logged in.");
//                    }
//                    break;
//                case 12:
//                    if (adminLoggedIn) {
//                        viewAllStudentEnrollments();
//                    } else {
//                        System.out.println("Admin not logged in.");
//                    }
//                    break;
//                case 13:
//                    exit = true;
//                    break;
//                default:
//                    System.out.println("Invalid choice.");
//            }
//        }
//
//    }

    private static void adminLogin(Admin admin) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        admin.login(username, password);
    }

    public static void createCourse() {
        System.out.print("Enter course title: ");
        String title = scanner.nextLine();
        System.out.print("Enter course content: ");
        String content = scanner.nextLine();
        System.out.print("Enter course criteria: ");
        String criteria = scanner.nextLine();
        courses.add(new Course(title, content, criteria));
        System.out.println("Course created.");
    }

    public static void modifyCourse() {
        System.out.print("Enter course title to modify: ");
        String title = scanner.nextLine();
        Course course = findCourseByTitle(title);
        if (course != null) {
            System.out.print("Enter new content: ");
            String content = scanner.nextLine();
            System.out.print("Enter new criteria: ");
            String criteria = scanner.nextLine();
            course.setContent(content);
            course.setCriteria(criteria);
            System.out.println("Course details modified.");
        } else {
            System.out.println("Course not found.");
        }
    }

    public static void enrollStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        Student student = new Student(name, id);

        // Check if the student is already in the list
        if (students.contains(student)) {
            System.out.println("Student is already enrolled.");
            return;
        }

        students.add(student);

        System.out.print("Enter course title to enroll in: ");
        String courseTitle = scanner.nextLine();
        Course course = findCourseByTitle(courseTitle);

        if (course != null) {
            // Check if the student is already enrolled in the course
            if (course.getStudents().contains(student)) {
                System.out.println("Student is already enrolled in this course.");
                students.remove(student); // Rollback the addition
                throw new IllegalArgumentException("Student is already enrolled in this course.");
            }

            course.enrollStudent(student);
            System.out.println("Student enrolled in course.");
        } else {
            System.out.println("Course not found.");
        }
    }


    public static void createAssignment() {
        System.out.print("Enter course title for assignment: ");
        String courseTitle = scanner.nextLine();
        Course course = findCourseByTitle(courseTitle);
        if (course != null) {
            System.out.print("Enter assignment title: ");
            String title = scanner.nextLine();
            System.out.print("Enter assignment content: ");
            String content = scanner.nextLine();
            System.out.print("Enter deadline (yyyy-MM-dd): ");
            String deadlineInput = scanner.nextLine();
            try {
                LocalDate deadline = LocalDate.parse(deadlineInput, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                Assignment assignment = new Assignment(title, content, deadline);
                course.addAssignment(assignment);
                System.out.println("Assignment created.");
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            }
        } else {
            System.out.println("Course not found.");
        }
    }

    public static void viewAssignments() {
        System.out.print("Enter course title to view assignments: ");
        String courseTitle = scanner.nextLine();
        Course course = findCourseByTitle(courseTitle);
        if (course != null) {
            List<Assignment> assignments = course.getAssignments();
            if (assignments.isEmpty()) {
                System.out.println("No assignments found.");
            } else {
                assignments
                        .forEach(a -> System.out.println("Title: " + a.getTitle() + ", Deadline: " + a.getDeadline()));
            }
        } else {
            System.out.println("Course not found.");
        }
    }

    public static void extendDeadline() {
        System.out.print("Enter course title for assignment: ");
        String courseTitle = scanner.nextLine();
        Course course = findCourseByTitle(courseTitle);
        if (course != null) {
            System.out.print("Enter assignment title: ");
            String title = scanner.nextLine();
            Assignment assignment = findAssignmentByTitle(course, title);
            if (assignment != null) {
                System.out.print("Enter new deadline (yyyy-MM-dd): ");
                String deadlineInput = scanner.nextLine();
                try {
                    LocalDate newDeadline = LocalDate.parse(deadlineInput, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    assignment.extendDeadline(newDeadline);
                } catch (Exception e) {
                    System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                }
            } else {
                System.out.println("Assignment not found.");
            }
        } else {
            System.out.println("Course not found.");
        }
    }

    public static void deleteAssignment() {
        System.out.print("Enter course title for assignment: ");
        String courseTitle = scanner.nextLine();
        Course course = findCourseByTitle(courseTitle);
        if (course != null) {
            System.out.print("Enter assignment title: ");
            String title = scanner.nextLine();
            Assignment assignment = findAssignmentByTitle(course, title);
            if (assignment != null) {
                course.getAssignments().remove(assignment);
                System.out.println("Assignment deleted.");
            } else {
                System.out.println("Assignment not found.");
            }
        } else {
            System.out.println("Course not found.");
        }
    }

    public static void trackStudentProgress() {
        System.out.print("Enter student ID to track: ");
        String id = scanner.nextLine();
        Student student = findStudentById(id);
        if (student != null) {
            List<Assignment> assignments = student.getAssignments();
            if (assignments.isEmpty()) {
                System.out.println("No assignments found for this student.");
            } else {
                assignments.forEach(
                        a -> System.out.println("Title: " + a.getTitle() + ", State: " + a.getClass().getSimpleName()));
            }
        } else {
            System.out.println("Student not found.");
        }
    }

    public static void provideFeedback() {
        System.out.print("Enter course title for assignment: ");
        String courseTitle = scanner.nextLine();
        Course course = findCourseByTitle(courseTitle);
        if (course != null) {
            System.out.print("Enter assignment title: ");
            String title = scanner.nextLine();
            Assignment assignment = findAssignmentByTitle(course, title);
            if (assignment != null) {
                assignment.provideFeedback();
            } else {
                System.out.println("Assignment not found.");
            }
        } else {
            System.out.println("Course not found.");
        }
    }

    private static Course findCourseByTitle(String title) {
        for (Course course : courses) {
            if (course.getTitle().equalsIgnoreCase(title)) {
                return course;
            }
        }
        return null;
    }

    private static Assignment findAssignmentByTitle(Course course, String title) {
        for (Assignment assignment : course.getAssignments()) {
            if (assignment.getTitle().equalsIgnoreCase(title)) {
                return assignment;
            }
        }
        return null;
    }

    private static Student findStudentById(String id) {
        for (Student student : students) {
            if (student.getId().equalsIgnoreCase(id)) {
                return student;
            }
        }
        return null;
    }
    public static void viewAllCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses available.");
        } else {
            for (Course course : courses) {
                System.out.println("Course Title: " + course.getTitle());
                System.out.println("Content: " + course.getContent());
                System.out.println("Criteria: " + course.getCriteria());
                System.out.println();
            }
        }
    }

    public static void viewAllStudentEnrollments() {
        if (students.isEmpty()) {
            System.out.println("No students enrolled.");
        } else {
            for (Student student : students) {
                System.out.println("Student Name: " + student.getName());
                System.out.println("Student ID: " + student.getId());
                System.out.println("Enrolled Courses:");
                for (Course course : courses) {
                    if (course.getStudents().contains(student)) {
                        System.out.println("  - " + course.getTitle());
                    }
                }
                System.out.println();
            }
        }
    }

}
