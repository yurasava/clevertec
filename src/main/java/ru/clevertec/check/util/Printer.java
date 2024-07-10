package ru.clevertec.check.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Printer {
    private Printer() {
        throw new IllegalStateException("Utility class");
    }

    public static void print(File file) {
        String csvFile = file.toString();
        String line;
        String cvsSplitBy = ";";


        List<String[]> rows = new ArrayList<>();
        int columnCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);
                rows.add(data);
                if (data.length > columnCount) {
                    columnCount = data.length;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (rows.isEmpty()) {
            System.out.println("CSV file is empty.");
            return;
        }

        int[] maxWidths = new int[columnCount];

        for (String[] row : rows) {
            for (int i = 0; i < row.length; i++) {
                if (row[i].length() > maxWidths[i]) {
                    maxWidths[i] = row[i].length();
                }
            }
        }

        printSeparator(maxWidths);
        for (String[] row : rows) {
            printRow(row, maxWidths);
            printSeparator(maxWidths);
        }
    }

    private static void printSeparator(int[] maxWidths) {
        for (int width : maxWidths) {
            System.out.print("+");
            for (int i = 0; i < width + 2; i++) {
                System.out.print("-");
            }
        }
        System.out.println("+");
    }

    private static void printRow(String[] row, int[] maxWidths) {
        for (int i = 0; i < maxWidths.length; i++) {
            if (i < row.length) {
                System.out.print("| " + padRight(row[i], maxWidths[i]) + " ");
            } else {
                System.out.print("| " + padRight("", maxWidths[i]) + " ");
            }
        }
        System.out.println("|");
    }

    private static String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }
}
