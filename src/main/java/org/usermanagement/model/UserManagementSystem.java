package org.usermanagement.model;

import org.assessmentmanagement.Admin;
import org.assessmentmanagement.AssessmentManagement;
import org.coursemanagement.CourseManagementSubsystem;
import org.usermanagement.util.UniqueIdGenerator;
import org.usermanagement.util.Pattern;

import java.io.*;
import java.util.*;

public class UserManagementSystem {

    private UserAccount loggedInUser;

    private static UserManagementSystem instance;
    private Map<String, UserAccount> users;
    private File userFile;
    private boolean adminLoggedIn = false;
    private boolean lecturerLoggedIn = false;


    private UserManagementSystem() throws IOException {
        loggedInUser = null;
        users = new HashMap<>();

        // Load properties from application.properties file
        // (Review Comment) Try to avoid hard coded file path, it will be hard to manage later.
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(inputStream);
        }

        // Get user file path from properties
        String userFilePath = properties.getProperty("user.file.path");
        userFile = new File(userFilePath);
        loadUsersFromFile(); // Load existing users from file when the system starts
    }

    // Method to set the user file path (for testing purposes)
    public void setUserFilePath(String userFilePath) {
        this.userFile = new File(userFilePath);
        // Reload users from the new file path
    }

    // Singleton getInstance method
    // (Review Comment)	Ensure the singleton pattern is correctly implemented, to prevent multiple instances in multi-threaded environment.
    public static synchronized UserManagementSystem getInstance() throws IOException {
        if (instance == null) {
            instance = new UserManagementSystem();
        }
        return instance;
    }

    // Method to sign up a new user
    public void signUp() {
        Scanner scanner = new Scanner(System.in);

        // Ask for user details
        System.out.print("Enter your full name: ");
        String fullname = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        // Check if user with the same email already exists
        //(Review Comment)	Provide more descriptive error messages to the user.
        if (users.containsKey(email)) {
            System.out.println("User with this email already exists, can you please sign up with different email");
            return; // Exit sign-up process
        }

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        System.out.print("Enter your phone number: ");
        String phone = scanner.nextLine();

        // Ask for user role
        System.out.println("Choose your role:");
        System.out.println("1. Student");
        System.out.println("2. Lecturer");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        // Validate user role choice
        UserRole role;
        switch (choice) {
            case 1:
                role = UserRole.STUDENT;
                break;
            case 2:
                role = UserRole.LECTURER;
                break;
            default:
                System.out.println("Invalid choice. Defaulting to Student role.");
                role = UserRole.STUDENT;
        }

        String userId = UniqueIdGenerator.generateId(); // Generate user ID
        UserAccount newUser = new UserAccount(userId, fullname, email, password, phone, role, "Active");
        users.put(email, newUser);
        saveUserToFile(newUser); // Save new user to file
        System.out.println("User signed up successfully! User ID: " + userId);
        scanner.nextLine(); // Consume newline
    }

    // Method to authenticate a user
    public boolean login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        if (email.equalsIgnoreCase("admin") & password.equals("admin")) {
            adminLoggedIn = true;
            System.out.println("Admin Login successful! Welcome to Admin Panel");
        } else if (users.containsKey(email)) {
            UserAccount user = users.get(email);
            if (user.getUserStatus().equalsIgnoreCase("Inactive")) {
                System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("*****************************     Account is not active. Please activate your account to continue.       ***********************************");
                System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");

                return false;
            } else if (user.login(email, password) && user.getRole().equals(UserRole.STUDENT)) {
                loggedInUser = user;
                Pattern.drawPattern();
                System.out.println("Login successful! Welcome, " + user.getFullname() + "!");
                return true;
            } else if (user.login(email, password) && user.getRole().equals(UserRole.LECTURER)) {
                lecturerLoggedIn = true;
                Pattern.drawPattern();
                System.out.println("Login successful! Welcome, " + user.getFullname() + "!");
                return true;
            } else {
                System.out.println("Incorrect password. Please try again.");
            }
        } else {
//            (Review Comments) Provide more descriptive error messages to the user.
            System.out.println("User with this email does not exist, Can you please try again with your valid credentials.");
        }
        return false;
    }

    // Method to logout a user
    public void logout() {
        if (adminLoggedIn) {
            adminLoggedIn = false;
            System.out.println("Logged out successfully!");
            System.out.println("-----      ----------          --------         ---------         ------");
        } else if (lecturerLoggedIn) {
            lecturerLoggedIn = false;
            System.out.println("Logged out successfully!");
            System.out.println("-----      ----------          --------         ---------         ------");
        } else if (loggedInUser != null) {
            loggedInUser = null;
            System.out.println("Logged out successfully!");
            System.out.println("-----      ----------          --------         ---------         ------");
        } else {
            System.out.println("No user is currently logged in.");
        }
    }

    // Method to load users from file
    private void loadUsersFromFile() throws IOException {
        if (!userFile.exists()) {
            userFile.createNewFile();
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    String userId = parts[0];
                    String fullname = parts[1];
                    String email = parts[2];
                    String password = parts[3];
                    String phone = parts[4];
                    UserRole role = UserRole.valueOf(parts[5]);
                    String userStatus = parts[6];
                    users.put(email, new UserAccount(userId, fullname, email, password, phone, role, userStatus));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users from file: " + e.getMessage());
        }
    }

    // Method to save user to file
    private void saveUserToFile(UserAccount user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile, true))) {
            writer.newLine();
            writer.write(user.getUserId() + "," + user.getFullname() + "," + user.getEmail() + "," + user.getPassword() + "," + user.getPhone() + "," + user.getRole() + "," + user.getUserStatus());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error saving user to file: " + e.getMessage());
        }
    }

    // Method to save all users to file
    private void saveUsersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile))) {
            for (UserAccount user : users.values()) {
                writer.write(user.getUserId() + "," + user.getFullname() + "," + user.getEmail() + "," + user.getPassword() + "," + user.getPhone() + "," + user.getRole() + "," + user.getUserStatus());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving users to file: " + e.getMessage());
        }
    }

    // Method to activate a user's account
    private void activateAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        UserAccount userAccount = users.get(email);
        if (userAccount.getUserStatus().equalsIgnoreCase("Active")) {
            System.out.println("User is already activated, please proceed to login.");
        } else if (userAccount.getUserStatus().equalsIgnoreCase("Inactive") && userAccount != null && userAccount.getPassword().equals(password)) {
            userAccount.activateAccount(userAccount);
            System.out.println("Account activated successfully, please proceed to login");
            users.put(email, userAccount);
            saveUsersToFile();
        } else {
            System.out.println("User with given credentials doesn't exist, please try again with correct credentials.");
        }

    }

    // Method to deactivate the logged-in user's account
    private void deactivateAccount() {
        if (loggedInUser != null) {
            UserAccount userAccount = users.get(loggedInUser.getEmail());
            if (userAccount != null && userAccount.getUserStatus().equalsIgnoreCase("Active")) {
                userAccount.deactivateAccount(userAccount);
                System.out.println("Account deactivated successfully.");
                users.put(loggedInUser.getEmail(), userAccount);
                saveUsersToFile();
                logout();
            }
        } else {
            System.out.println("No user is currently logged in.");
        }
    }

    private void accessDashboard() {
        System.out.println("Accessing dashboard...");
    }

    private void exit() {
        System.out.println("Exiting...");
        System.exit(0);
    }


    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        CourseManagementSubsystem courseManagementSubsystem = new CourseManagementSubsystem();
        AssessmentManagement assessmentManagement = new AssessmentManagement();
        Admin admin = Admin.getInstance();

        while (true) {
            if (adminLoggedIn) {
                System.out.println("=======********** E-Learning System *********========");
                System.out.println("1. Create Course");
                System.out.println("2. Modify Course Details");
                System.out.println("3. Enroll Student to Course");
                System.out.println("4. Create Assignment");
                System.out.println("5. View Assignments");
                System.out.println("6. Extend Assignment Deadline");
                System.out.println("7. Delete Assignment");
                System.out.println("8. Track Student Progress");
                System.out.println("9. Provide Feedback");
                System.out.println("10. View All Courses");
                System.out.println("11. View All Student Enrollments");
                System.out.println("12. Logout");
                System.out.println("13. Exit");
            } else if (lecturerLoggedIn) {
                System.out.println("=======********** E-Learning System *********========");
                System.out.println("\n Lecturer Menu:");
                System.out.println("1. Create Quiz");
                System.out.println("2. Add Question to Quiz");
                System.out.println("3. Set Instructions for Quiz");
                System.out.println("4. Set Passing Criteria for Quiz");
                System.out.println("5. Check Quiz Status");
                System.out.println("6. Finish Quiz");
                System.out.println("7. Assign Grades for Quiz");
                System.out.println("8. Exit");
            } else if (loggedInUser == null) {
                // Menu options for when no user is logged in
                System.out.println("=======********** WELCOME TO E-Learning System *********========");

                System.out.println("Please select an option from below to start your learning journey.");

                System.out.println("1. Sign Up");
                System.out.println("2. Log In");
                System.out.println("3. Activate Account");
                System.out.println("4. Exit");
            } else {
                // Menu options for when a user is logged in
                System.out.println("=======********** E-Learning System *********========");

                System.out.println("----------   Please select your choice  ------------");
                System.out.println("1. Access Dashboard");
                System.out.println("2. View Grades");
                System.out.println("3. View Grades by Course");
                System.out.println("4. Make Payment");
                System.out.println("5. Enrol in Course");
                System.out.println("6. Drop Course");
                System.out.println("7. Manage Enrolment");
                System.out.println("8. Deactivate Account");
                System.out.println("9. Logout");
                System.out.println("10. Exit");
            }

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (adminLoggedIn) {
                switch (choice) {
                    case 1:
                        if (adminLoggedIn) {
                            courseManagementSubsystem.createCourse();
                        }
                        break;
                    case 2:
                        if (adminLoggedIn) {
                            courseManagementSubsystem.modifyCourse();
                        }
                        break;
                    case 3:
                        if (adminLoggedIn) {
                            courseManagementSubsystem.enrollStudent();
                        }
                        break;
                    case 4:
                        if (adminLoggedIn) {
                            courseManagementSubsystem.createAssignment();
                        }
                        break;
                    case 5:
                        if (adminLoggedIn) {
                            courseManagementSubsystem.viewAssignments();
                        }
                        break;
                    case 6:
                        if (adminLoggedIn) {
                            courseManagementSubsystem.extendDeadline();
                        }
                        break;
                    case 7:
                        if (adminLoggedIn) {
                            courseManagementSubsystem.deleteAssignment();
                        }
                        break;
                    case 8:
                        if (adminLoggedIn) {
                            courseManagementSubsystem.trackStudentProgress();
                        }
                        break;
                    case 9:
                        if (adminLoggedIn) {
                            courseManagementSubsystem.provideFeedback();
                        }
                        break;
                    case 10:
                        if (adminLoggedIn) {
                            courseManagementSubsystem.viewAllCourses();
                        }
                        break;
                    case 11:
                        if (adminLoggedIn) {
                            courseManagementSubsystem.viewAllStudentEnrollments();
                        }
                        break;
                    case 12:
                        if (adminLoggedIn) {
                            logout();
                        }
                        break;
                    case 13:
                        exit();
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } else if (lecturerLoggedIn) {
                // Handle menu options for when lecturer is logged in
                switch (choice) {
                    case 1:
                        admin.createQuiz();
                        break;
                    case 2:
                        assessmentManagement.addQuestionToQuiz();
                        break;
                    case 3:
                        assessmentManagement.setInstructionsForQuiz();
                        break;
                    case 4:
                        assessmentManagement.setPassingCriteriaForQuiz();
                        break;
                    case 5:
                        assessmentManagement.checkQuizStatus();
                        break;
                    case 6:
                        assessmentManagement.finishQuiz();
                        break;
                    case 7:
                        assessmentManagement.assignGradesForQuiz();
                        break;
                    case 8:

                        System.out.println("Exiting...");
                        exit();
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } else if (loggedInUser == null) {
                // Handle menu options for when no user is logged in
                switch (choice) {
                    case 1:
                        signUp();
                        break;
                    case 2:
                        login();
                        break;
                    case 3:
                        activateAccount();
                        break;
                    case 4:
                        exit();
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } else if (loggedInUser != null) {
                // Handle menu options for when a user is logged in
                switch (choice) {
                    case 1:
                        accessDashboard();
                        break;
                    case 2:
                        viewGrades();
                        break;
                    case 3:
                        getGradeByCourse();
                        break;
                    case 4:
                        makePayment();
                        break;
                    case 5:
                        enrolInCourse();
                        break;
                    case 6:
                        dropCourse();
                        break;
                    case 7:
                        manageEnrolment();
                        break;
                    case 8:
                        deactivateAccount();
                        break;
                    case 9:
                        logout();
                        break;
                    case 10:
                        exit();
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }
        }
    }

    // Dummy methods for new functionalities
    private void viewGrades() {
        System.out.println("Viewing grades...");
        Grade grade = new Grade("G123", loggedInUser.getUserId(), "C101", "A");
        grade.getGradeByUser();
    }

    private void getGradeByCourse() {
        Grade grade = new Grade();
        grade.getGradeByCourse();
    }


    private void makePayment() {
        System.out.println("Making payment...");
        System.out.println("Your account is due by: $500");

        Payment payment = new Payment("P123", 500.0f, new Date());
        payment.pay(loggedInUser);
    }

    private void enrolInCourse() {
        System.out.println("Enrolling in course...");
        Enrolment enrolment = new Enrolment("E123", "C101", loggedInUser.getUserId(), new
                Date());
        enrolment.enrol();
    }

    private void dropCourse() {
        System.out.println("Dropping course...");
        Enrolment enrolment = new Enrolment("E123", "C101", loggedInUser.getUserId(), new Date());
        enrolment.drop();
    }

    private void manageEnrolment() {
        System.out.println("Managing enrolment...");
        Enrolment enrolment = new Enrolment("E123", "C101", loggedInUser.getUserId(), new Date());
        enrolment.manage();
    }


}
