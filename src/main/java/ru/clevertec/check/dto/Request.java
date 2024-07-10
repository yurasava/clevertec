package ru.clevertec.check.dto;

import java.util.Map;

public record Request(Map<Integer, Integer> idAndQuantityOfProduct,
                      Integer discountCardNumber,
                      Double balanceDebitCard) {
}
