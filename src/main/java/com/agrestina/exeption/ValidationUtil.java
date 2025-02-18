package com.agrestina.exeption;

import com.agrestina.domain.orderedItems.OrderedItem;
import com.agrestina.dto.order.OrderedItemDTO;

import java.math.BigDecimal;
import java.util.List;

public class ValidationUtil {

    public static void validateNotNullOrBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new ValidationException(String.format("O campo %s não pode ser nulo ou em branco", fieldName));
        }
    }

    public static void validateNotNull(Object value, String fieldName) {
        if (value == null) {
            throw new ValidationException(String.format("O campo %s não pode ser nulo", fieldName));
        }
    }

    public static void validatePositive(BigDecimal value, String fieldName) {
        if (value == null || value.equals(BigDecimal.ZERO) || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException(String.format("O campo %s deve ser maior que zero", fieldName));
        }
    }

    public static void validateMin(Integer value, String fieldName) {
        if (value == null || value < 1 ) {
            throw new ValidationException(String.format("O campo %s deve ser maior que zero", fieldName));
        }
    }

    public static void validateList(List<OrderedItemDTO> list, String fieldName) {
        list.forEach(item -> {
            if (item.quantity() <= 0) {
                throw new ValidationException(String.format("O campo %s deve ser maior que zero", fieldName));
            }
        });
    }
}