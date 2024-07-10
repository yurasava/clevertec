package ru.clevertec.check.dto;

public record CardItem(Integer quantity,
                       String description,
                       Double price,
                       Integer discount,
                       Double totalPrice) {
}
