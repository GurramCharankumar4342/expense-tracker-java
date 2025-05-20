package com.expensetracker;
import java.time.LocalDate;
import java.util.Scanner;

public class ExpenseTracker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TransactionManager manager = new TransactionManager();

        while (true) {
            System.out.println("\nExpense Tracker Menu:");
            System.out.println("1. Add Income");
            System.out.println("2. Add Expense");
            System.out.println("3. Load Transactions from File");
            System.out.println("4. Save Transactions to File");
            System.out.println("5. View Monthly Summary");
            System.out.println("6. Exit");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (choice == 1 || choice == 2) {
                String type = (choice == 1) ? "INCOME" : "EXPENSE";

                System.out.print("Enter date (YYYY-MM-DD): ");
                LocalDate date = LocalDate.parse(scanner.nextLine());

                System.out.print("Enter category (" + (type.equals("INCOME") ? "Salary/Business" : "Food/Rent/Travel") + "): ");
                String category = scanner.nextLine();

                System.out.print("Enter amount: ");
                double amount = scanner.nextDouble();
                scanner.nextLine(); // consume newline

                manager.addTransaction(new Transaction(date, type, category, amount));
                System.out.println(type + " added.");
            } else if (choice == 3) {
                System.out.print("Enter file name to load: ");
                String filename = scanner.nextLine();
                manager.loadFromFile(filename);
                System.out.println("Data loaded.");
            } else if (choice == 4) {
                System.out.print("Enter file name to save: ");
                String filename = scanner.nextLine();
                manager.saveToFile(filename);
                System.out.println("Data saved.");
            } else if (choice == 5) {
                System.out.print("Enter year (e.g., 2025): ");
                int year = scanner.nextInt();
                System.out.print("Enter month (1-12): ");
                int month = scanner.nextInt();
                manager.printMonthlySummary(year, month);
            } else if (choice == 6) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }

        scanner.close();
    }
}
