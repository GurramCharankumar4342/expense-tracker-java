package com.expensetracker;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class TransactionManager {
    private List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    public void loadFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                LocalDate date = LocalDate.parse(parts[0]);
                String type = parts[1];
                String category = parts[2];
                double amount = Double.parseDouble(parts[3]);
                transactions.add(new Transaction(date, type, category, amount));
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void saveToFile(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Transaction t : transactions) {
                bw.write(t.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    public void printMonthlySummary(int year, int month) {
        double incomeTotal = 0;
        double expenseTotal = 0;

        Map<String, Double> incomeByCategory = new HashMap<>();
        Map<String, Double> expenseByCategory = new HashMap<>();

        StringBuilder summary = new StringBuilder();
        summary.append("===== Monthly Summary for ").append(year).append("-").append(month).append(" =====\n");

        for (Transaction t : transactions) {
            if (t.getDate().getYear() == year && t.getDate().getMonthValue() == month) {
                if (t.getType().equalsIgnoreCase("INCOME")) {
                    incomeTotal += t.getAmount();
                    incomeByCategory.merge(t.getCategory(), t.getAmount(), Double::sum);
                } else {
                    expenseTotal += t.getAmount();
                    expenseByCategory.merge(t.getCategory(), t.getAmount(), Double::sum);
                }
            }
        }

        summary.append("Total Income: $").append(incomeTotal).append("\n");
        for (String category : incomeByCategory.keySet()) {
            summary.append("  ").append(category).append(": $").append(incomeByCategory.get(category)).append("\n");
        }

        summary.append("Total Expense: $").append(expenseTotal).append("\n");
        for (String category : expenseByCategory.keySet()) {
            summary.append("  ").append(category).append(": $").append(expenseByCategory.get(category)).append("\n");
        }

        summary.append("Net Savings: $").append(incomeTotal - expenseTotal).append("\n");

        // Print to console
        System.out.println(summary);

        // Save to summary_output.txt
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("summary_output.txt"))) {
            writer.write(summary.toString());
        } catch (IOException e) {
            System.out.println("Error writing summary to file: " + e.getMessage());
        }
    }
}

