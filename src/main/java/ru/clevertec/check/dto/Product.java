package ru.clevertec.check.dto;

public record Product(Integer id,
                      String description,
                      Double price,
                      Integer quantity,
                      Boolean wholesaleProduct) {
}
