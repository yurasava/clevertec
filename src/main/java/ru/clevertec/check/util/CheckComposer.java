package ru.clevertec.check.util;

import ru.clevertec.check.entity.ShoppingCart;
import ru.clevertec.check.dto.CardItem;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CheckComposer {

    private CheckComposer() {
        throw new IllegalStateException("Utility class");
    }

    public static String composeCheck(ShoppingCart shoppingCart) {

        StringBuilder check = new StringBuilder();

        check.append("Date;Time\n");
        check.append(shoppingCart.getDate()).append(";").append(shoppingCart.getTime()).append("\n");

        check.append("\nQTY;DESCRIPTION;PRICE;DISCOUNT;TOTAL\n");
        shoppingCart.getCardItems().forEach(cardItem -> check.append(cardItem.quantity()).append(";")
                .append(cardItem.description()).append(";")
                .append(cardItem.price()).append("$").append(";")
                .append(getDiscount(cardItem)).append("%").append(";")
                .append(cardItem.totalPrice()).append("$").append("\n"));

        if (shoppingCart.getDiscountCard().number() != null) {
            check.append("\nDISCOUNT CARD;DISCOUNT PERCENTAGE\n");
            check.append(shoppingCart.getDiscountCard().number()).append(";")
                    .append(shoppingCart.getDiscountCard().discountPercent()).append("%").append("\n");
        }

        check.append("\nTOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT\n");
        check.append(getTotalPrice(shoppingCart)).append("$").append(";")
                .append(getTotalDiscount(shoppingCart)).append("$").append(";")
                .append(getTotalPriceWithDiscount(shoppingCart)).append("$").append("\n");

        return check.toString();
    }

    private static String getTotalPriceWithDiscount(ShoppingCart shoppingCart) {
        double totalPriceWithDiscount = 0;
        for (CardItem cardItem : shoppingCart.getCardItems()) {
            totalPriceWithDiscount += cardItem.totalPrice() - getDiscount(cardItem);
        }
        BigDecimal bd = new BigDecimal(totalPriceWithDiscount);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.toString();
    }

    private static Double getDiscount(CardItem cardItem) {
        double discount = cardItem.discount() * cardItem.totalPrice() / 100;
        BigDecimal bd = new BigDecimal(discount);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private static Double getTotalPrice(ShoppingCart shoppingCart) {
        double totalPrice = 0;
        for (CardItem cardItem : shoppingCart.getCardItems()) {
            totalPrice += cardItem.totalPrice();
        }
        BigDecimal bd = new BigDecimal(totalPrice);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private static Double getTotalDiscount(ShoppingCart shoppingCart) {
        double totalDiscount = 0;
        for (CardItem cardItem : shoppingCart.getCardItems()) {
            totalDiscount += cardItem.discount() * cardItem.totalPrice() / 100;
        }
        BigDecimal bd = new BigDecimal(totalDiscount);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
