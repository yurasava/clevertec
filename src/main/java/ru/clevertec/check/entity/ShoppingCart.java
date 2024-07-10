package ru.clevertec.check.entity;

import ru.clevertec.check.dto.CardItem;
import ru.clevertec.check.dto.DiscountCard;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private String date;
    private String time;

    private List<CardItem> cardItems = new ArrayList<>();
    private DiscountCard discountCard;
    private Double balanceDebitCard;

    public ShoppingCart() {
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setCardItems(List<CardItem> cardItems) {
        this.cardItems = cardItems;
    }

    public void setDiscountCard(DiscountCard discountCard) {
        this.discountCard = discountCard;
    }

    public void setBalanceDebitCard(Double balanceDebitCard) {
        this.balanceDebitCard = balanceDebitCard;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public List<CardItem> getCardItems() {
        return cardItems;
    }

    public DiscountCard getDiscountCard() {
        return discountCard;
    }

    public Double getBalanceDebitCard() {
        return balanceDebitCard;
    }
}
