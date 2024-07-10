package ru.clevertec.check.util;

import ru.clevertec.check.dto.CardItem;
import ru.clevertec.check.dto.Product;
import ru.clevertec.check.entity.ShoppingCart;
import ru.clevertec.check.dto.DiscountCard;
import ru.clevertec.check.dto.Request;
import ru.clevertec.check.exception.BadRequestException;
import ru.clevertec.check.exception.NotEnoughMoneyException;

import java.text.SimpleDateFormat;
import java.util.Map;

public final class ShoppingCartProcessor {

    private static final Map<Integer, Product> CSV_PRODUCTS = CsvProductLoader.getProducts();
    private static final Map<Integer, DiscountCard> discountCards = CsvDiscountCardLoader.getDiscountCards();

    private ShoppingCartProcessor() {
        throw new IllegalStateException("Utility class");
    }

    public static ShoppingCart generateShoppingCart(Request request) {
        ShoppingCart shoppingCart = new ShoppingCart();

        generateDateAndTime(shoppingCart);
        generateDiscountCard(request, shoppingCart);
        generateItems(request, shoppingCart);
        generateBalanceDebitCard(request, shoppingCart);

        return shoppingCart;
    }


    private static void generateItems(Request request, ShoppingCart myShoppingCart) throws NotEnoughMoneyException, BadRequestException {
        if (RequestValidator.validateProducts(request.idAndQuantityOfProduct(), CSV_PRODUCTS)) {
            myShoppingCart.setCardItems(request.idAndQuantityOfProduct().entrySet().stream()
                    .map(entry -> new CardItem(
                            entry.getValue(),
                            CSV_PRODUCTS.get(entry.getKey()).description(),
                            CSV_PRODUCTS.get(entry.getKey()).price(),
                            setDiscount(CSV_PRODUCTS.get(entry.getKey()), myShoppingCart),
                            entry.getValue() * CSV_PRODUCTS.get(entry.getKey()).price()
                    )).toList()
            );
        } else {
            RecordResult.recordErrorResult("ERROR\nBAD REQUEST", request.saveToFile());
            throw new BadRequestException();
        }
    }

    private static Integer setDiscount(Product product, ShoppingCart myShoppingCart) {
        int discountPercent;
        if (product.wholesaleProduct() && product.quantity() >= 5) {
            discountPercent = 10;
        } else {
            discountPercent = myShoppingCart.getDiscountCard().discountPercent();
        }
        return discountPercent;
    }

    private static void generateDateAndTime(ShoppingCart myShoppingCart) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String dateAndTime = format.format(System.currentTimeMillis());

        String date = dateAndTime.split(" ")[0];
        String time = dateAndTime.split(" ")[1];

        myShoppingCart.setDate(date);
        myShoppingCart.setTime(time);
    }

    private static void generateDiscountCard(Request request, ShoppingCart myShoppingCart) {
        if (RequestValidator.validateDiscountCard(request.discountCardNumber())) {
            myShoppingCart.setDiscountCard(new DiscountCard(
                    discountCards.get(request.discountCardNumber()).id(),
                    discountCards.get(request.discountCardNumber()).number(),
                    discountCards.get(request.discountCardNumber()).discountPercent()));
        } else {
            myShoppingCart.setDiscountCard(new DiscountCard(null, null, 2));
        }
    }

    private static void generateBalanceDebitCard(Request request, ShoppingCart myShoppingCart) {
        Double balanceDebitCard = request.balanceDebitCard();

        if (RequestValidator.validateBalanceDebitCard(request)) {
            myShoppingCart.setBalanceDebitCard(balanceDebitCard);
        } else {
            RecordResult.recordErrorResult("ERROR\nNOT ENOUGH MONEY", request.saveToFile());
            throw new NotEnoughMoneyException();
        }
    }
}
