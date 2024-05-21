package org.usermanagement.model;

public class User {
    private String userId;
    private String fullname;
    private String email;
    private String password;
    private String phone;
    private UserRole role;

    public User(String userId, String fullname, String email, String password, String phone, UserRole role) {
        this.userId = userId;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
    }

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    // Login method
    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }
}
