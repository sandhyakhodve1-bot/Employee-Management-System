package com.expense.app;

import java.sql.*;
import java.util.Scanner;

public class ExpenseApp {
    private static final String URL = "jdbc:mysql://localhost:3306/expensedb";
    private static final String USER = "root";
    private static final String PASSWORD = "root123"; 

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== SMART EXPENSE TRACKER ===");
            System.out.println("1. Add Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. Delete Expense");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    addExpense(scanner);
                    break;
                case 2:
                    viewExpenses();
                    break;
                case 3:
                    deleteExpense(scanner);
                    break;
                case 4:
                    System.out.println("Exiting Expense Tracker. Thank you!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    private static void addExpense(Scanner scanner) {
        scanner.nextLine(); // Newline buffer क्लिअर करण्यासाठी

        System.out.print("Enter Title (e.g. Lunch, Shoes): ");
        String title = scanner.nextLine();

        System.out.print("Enter Category (e.g. Food, Shopping): ");
        String category = scanner.nextLine();

        System.out.print("Enter Amount: ");
        double amount = scanner.nextDouble();

        String query = "INSERT INTO expense (title, category, amount) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, title);
            pstmt.setString(2, category);
            pstmt.setDouble(3, amount);
            pstmt.executeUpdate();

            System.out.println("SUCCESS: Expense Added Successfully!");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewExpenses() {
        String query = "SELECT * FROM expense";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\n--- Expense List ---");
            double total = 0;
            boolean hasData = false;

            while (rs.next()) {
                hasData = true;
                double amt = rs.getDouble("amount");
                total += amt;
                System.out.println("ID: " + rs.getInt("expense_id") +
                        " | Title: " + rs.getString("title") +
                        " | Category: " + rs.getString("category") +
                        " | Amount: ₹" + amt +
                        " | Date: " + rs.getDate("expense_date"));
            }

            if (!hasData) {
                System.out.println("No expenses recorded yet.");
            } else {
                System.out.println("------------------------------------");
                System.out.println("TOTAL SPENT: ₹" + total);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteExpense(Scanner scanner) {
        System.out.print("Enter Expense ID to Delete: ");
        int id = scanner.nextInt();

        String query = "DELETE FROM expense WHERE expense_id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("SUCCESS: Expense Record Deleted!");
            } else {
                System.out.println("WARNING: Expense ID Not Found.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}