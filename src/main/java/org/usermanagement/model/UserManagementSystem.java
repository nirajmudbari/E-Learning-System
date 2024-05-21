package org.usermanagement.model;

import org.usermanagement.util.UniqueIdGenerator;
import org.usermanagement.util.Utils;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserManagementSystem {

    private UserAccount loggedInUser;

    private static UserManagementSystem instance;
    private Map<String, UserAccount> users;
    private File userFile;

    private UserManagementSystem() throws IOException {
        loggedInUser = null;
        users = new HashMap<>();
        userFile = new File("./src/main/resources/users.txt");
        loadUsersFromFile(); // Load existing users from file when the system starts
    }

    // Singleton getInstance method
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
        if (users.containsKey(email)) {
            System.out.println("User with this email already exists.");
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
        if (users.containsKey(email)) {
            UserAccount user = users.get(email);
            if (user.getUserStatus().equalsIgnoreCase("Inactive")) {
                System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("*****************************     Account is not active. Please activate your account to continue.       ***********************************");
                System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");

                return false;
            }
            if (user.login(email, password)) {
                loggedInUser = user;
                Utils.drawPattern();
                System.out.println("Login successful! Welcome, " + user.getFullname() + "!");
                return true;
            } else {
                System.out.println("Incorrect password. Please try again.");
            }
        } else {
            System.out.println("User with this email does not exist.");
        }
        return false;
    }

    // Method to logout a user
    public void logout() {
        if (loggedInUser != null) {
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
        // Your dashboard logic here
    }

    private void exit() {
        System.out.println("Exiting...");
        System.exit(0);
    }


    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            if (loggedInUser == null) {
                // Menu options for when no user is logged in
                System.out.println("=======********** WELCOME TO USER MANAGEMENT SUBSYSTEM *********========");

                System.out.println("Please select an option from below to start your learning journey.");

                System.out.println("1. Sign Up");
                System.out.println("2. Log In");
                System.out.println("3. Activate Account");
                System.out.println("4. Exit");
            } else {
                // Menu options for when a user is logged in
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

            if (loggedInUser == null) {
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
            } else {
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
//
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
