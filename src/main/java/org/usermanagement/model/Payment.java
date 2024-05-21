package org.usermanagement.model;

import java.util.Date;
import java.util.Scanner;

public class Payment {
    private String paymentId;
    private float amount;
    private Date paymentDate;

    public Payment(String paymentId, float amount, Date paymentDate) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public float getAmount() {
        return amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void pay(UserAccount userAccount) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter amount to pay: ");
        float paymentAmount = scanner.nextFloat();

        float remainingAmount = userAccount.getBalance();

        if (paymentAmount >= remainingAmount) {
            System.out.println("Payment made successfully.");
            System.out.println("Payment ID: " + paymentId + ", Amount: " + remainingAmount + ", Date: " + paymentDate);
            userAccount.setBalance(0);
            System.out.println("Full payment made. No remaining balance.");
        } else {
            System.out.println("Partial payment made successfully.");
            System.out.println("Payment ID: " + paymentId + ", Amount: " + paymentAmount + ", Date: " + paymentDate);
            userAccount.setBalance(remainingAmount - paymentAmount);
            System.out.println("Remaining balance: $" + (remainingAmount - paymentAmount));
        }
    }

    public void viewPayment() {
        System.out.println("Viewing payment details.");
        System.out.println("Payment ID: " + paymentId + ", Amount: " + amount + ", Date: " + paymentDate);
    }
}
