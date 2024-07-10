package ru.clevertec.check;

import ru.clevertec.check.dto.Request;
import ru.clevertec.check.entity.ShoppingCart;
import ru.clevertec.check.util.*;

public class CheckRunner {
    private static final String DISCOUNT_CARD_PATH = "src/main/resources/discountCards.csv";

    public static void main(String[] args) {

        Request request = ParseCommand.parseCommand(args);

        CsvProductLoader.loadProducts(request.pathToFile());
        CsvDiscountCardLoader.loadDiscountCards(DISCOUNT_CARD_PATH);

        ShoppingCart cart = ShoppingCartProcessor.generateShoppingCart(request);

        String check = CheckComposer.composeCheck(cart);

        RecordResult.recordResult(check, request.saveToFile());

        Printer.print(request.saveToFile());
    }
}
