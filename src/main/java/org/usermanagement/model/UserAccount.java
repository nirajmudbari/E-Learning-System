package org.usermanagement.model;

public class UserAccount extends User {
    private String userStatus;

    private float balance;

    public UserAccount(String userId, String fullname, String email, String password, String phone, UserRole role, String userStatus) {
        super(userId, fullname, email, password, phone, role);
        this.userStatus = userStatus;
        this.balance = 500; // Initialize balance to 0
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
    // Getters and setters
    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    // Additional methods for user account functionality
    public void activateAccount(UserAccount userAccount) {
        userAccount.setUserStatus("Active");
    }

    public void deactivateAccount(UserAccount userAccount) {
        userAccount.setUserStatus("Inactive");
    }


}

