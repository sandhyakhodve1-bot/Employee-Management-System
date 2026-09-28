package com.emp.app;

import java.sql.*;
import java.util.Scanner;

public class MainApp {
    private static final String URL = "jdbc:mysql://localhost:3306/employeedb";
    private static final String USER = "root";
    private static final String PASSWORD = "root123"; 

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== EMPLOYEE MANAGEMENT SYSTEM ===");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Delete Employee");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    addEmployee(scanner);
                    break;
                case 2:
                    viewEmployees();
                    break;
                case 3:
                    deleteEmployee(scanner);
                    break;
                case 4:
                    System.out.println("Exiting System. Thank you!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    private static void addEmployee(Scanner scanner) {
        System.out.print("Enter Name: ");
        String name = scanner.next();
        System.out.print("Enter Department: ");
        String dept = scanner.next();
        System.out.print("Enter Salary: ");
        double salary = scanner.nextDouble();

        String query = "INSERT INTO employee (emp_name, department, salary) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, name);
            pstmt.setString(2, dept);
            pstmt.setDouble(3, salary);
            pstmt.executeUpdate();

            System.out.println("SUCCESS: Employee Added Successfully!");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewEmployees() {
        String query = "SELECT * FROM employee";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\n--- Employee List ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("emp_id") +
                        " | Name: " + rs.getString("emp_name") +
                        " | Dept: " + rs.getString("department") +
                        " | Salary: " + rs.getDouble("salary"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteEmployee(Scanner scanner) {
        System.out.print("Enter Employee ID to Delete: ");
        int id = scanner.nextInt();

        String query = "DELETE FROM employee WHERE emp_id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("SUCCESS: Employee Deleted Successfully!");
            } else {
                System.out.println("WARNING: Employee ID Not Found.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}