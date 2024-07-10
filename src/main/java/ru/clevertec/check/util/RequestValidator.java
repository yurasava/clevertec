package ru.clevertec.check.util;

import ru.clevertec.check.dto.DiscountCard;
import ru.clevertec.check.dto.Product;
import ru.clevertec.check.dto.Request;
import ru.clevertec.check.exception.NotEnoughMoneyException;

import java.util.Map;
import java.util.Objects;

public class RequestValidator {

    private RequestValidator() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean validateProducts(Map<Integer, Integer> requestProducts, Map<Integer, Product> csvProducts) {

        if (!csvProducts.keySet().containsAll(requestProducts.keySet())) {
            return false;
        }

        for (Map.Entry<Integer, Integer> entry : requestProducts.entrySet()) {
            Integer productId = entry.getKey();
            Integer requiredQuantity = entry.getValue();

            Product product = csvProducts.get(productId);
            if (requiredQuantity > product.quantity()) {
                return false;
            }

            if (entry.getValue() > csvProducts.get(entry.getKey()).quantity()) {
                return false;
            }
        }
        return true;
    }

    public static boolean validateDiscountCard(Integer discountCardNumber) {

        Map<Integer, DiscountCard> discountCards = CsvDiscountCardLoader.getDiscountCards();

        return discountCards.entrySet().stream().anyMatch(entry -> Objects.equals(entry.getValue().number(), discountCardNumber)) || (discountCardNumber != null
                && discountCards.entrySet().contains(discountCardNumber));
    }

    public static boolean validateBalanceDebitCard(Request request) throws NotEnoughMoneyException {
        return request.balanceDebitCard() > 0;
    }
}
