package ru.clevertec.check.util;

import ru.clevertec.check.dto.DiscountCard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CsvDiscountCardLoader {

    private static final Map<Integer, DiscountCard> discountCards = new HashMap<>();

    private CsvDiscountCardLoader() {
        throw new IllegalStateException("Utility class");
    }

    public static void loadDiscountCards(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                int id = Integer.parseInt(parts[0]);
                int number = Integer.parseInt(parts[1]);
                int discountPercent = Integer.parseInt(parts[2]);
                DiscountCard discountCard = new DiscountCard(id, number, discountPercent);
                discountCards.put(number, discountCard);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<Integer, DiscountCard> getDiscountCards() {
        return discountCards;
    }
}
