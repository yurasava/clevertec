package ru.clevertec.check.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class RecordResult {

    private RecordResult() {
        throw new IllegalStateException("Utility class");
    }

    public static void recordResult(String result, String path) {
        File file = new File(path);

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

    public static void recordErrorResult(String message, String path) {
        recordResult(message, Objects.requireNonNullElse(path, "src/error_result.csv"));
    }
}
