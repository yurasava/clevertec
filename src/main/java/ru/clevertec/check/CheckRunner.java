package ru.clevertec.check;

import ru.clevertec.check.dto.Request;
import ru.clevertec.check.entity.ShoppingCart;
import ru.clevertec.check.util.CheckComposer;
import ru.clevertec.check.util.CsvDiscountCardLoader;
import ru.clevertec.check.util.CsvProductLoader;
import ru.clevertec.check.util.ParseCommand;
import ru.clevertec.check.util.Printer;
import ru.clevertec.check.util.RecordResult;
import ru.clevertec.check.util.ShoppingCartProcessor;

import java.io.File;

public class CheckRunner {
    private static final String PRODUCT_PATH = "src/main/resources/products.csv";
    private static final String DISCOUNT_CARD_PATH = "src/main/resources/discountCards.csv";
    private static final File RESULT = new File("src", "result.csv");

    public static void main(String[] args) {

        CsvProductLoader.loadProducts(PRODUCT_PATH);
        CsvDiscountCardLoader.loadDiscountCards(DISCOUNT_CARD_PATH);

        Request request = ParseCommand.parseCommand(args);

        ShoppingCart cart = ShoppingCartProcessor.generateShoppingCart(request);

        String check = CheckComposer.composeCheck(cart);

        RecordResult.recordResult(check, RESULT);

        Printer.print(RESULT);
    }
}
