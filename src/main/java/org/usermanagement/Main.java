package org.usermanagement;

import org.usermanagement.model.UserManagementSystem;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        UserManagementSystem system = UserManagementSystem.getInstance(); // Get the singleton instance
        system.displayMenu(); // Display the menu
    }
}