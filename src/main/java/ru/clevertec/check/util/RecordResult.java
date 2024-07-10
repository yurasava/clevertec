package ru.clevertec.check.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public class RecordResult {

    private RecordResult() {
        throw new IllegalStateException("Utility class");
    }

    public static void recordResult(String result, File file) {

        if (file.exists()) {
            file.delete();
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(file))) {
            writer.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void recordErrorResult(String message) {
        File file = new File("src", "result.csv");
        recordResult(message, file);
    }
}
