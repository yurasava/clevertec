package ru.clevertec.check.util;

import ru.clevertec.check.dto.Request;

import java.util.HashMap;
import java.util.Map;

public class ParseCommand {

    private ParseCommand() {
        throw new IllegalStateException("Utility class");
    }

    public static Request parseCommand(String[] command) {

        Map<Integer, Integer> idAndQuantityOfProduct = new HashMap<>();
        Integer discountCardNumber = null;
        Double balanceDebitCard = null;
        String pathToFile = null;
        String saveToFile = null;

        for (String arg : command) {
            if (arg.startsWith("discountCard=")) {
                discountCardNumber = Integer.valueOf(arg.split("=")[1]);
            } else if (arg.startsWith("balanceDebitCard=")) {
                balanceDebitCard = Double.parseDouble(arg.split("=")[1]);
            } else if (arg.startsWith("pathToFile=")) {
                pathToFile = arg.split("=")[1];
            } else if (arg.startsWith("saveToFile=")) {
                saveToFile = arg.split("=")[1];
            } else {
                String[] parts = arg.split("-");
                Integer productId = Integer.parseInt(parts[0]);
                Integer quantity = Integer.parseInt(parts[1]);

                idAndQuantityOfProduct.merge(productId, quantity, Integer::sum);
            }
        }

//        if (pathToFile == null || pathToFile.isBlank()) {
//            RecordResult.recordErrorResult("ERROR\nBAD REQUEST", saveToFile);
//        }

        return new Request(idAndQuantityOfProduct, discountCardNumber, balanceDebitCard, pathToFile, saveToFile);
    }
}
