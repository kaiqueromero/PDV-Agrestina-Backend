package com.agrestina.exeption;

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

    public static void validatePositive(Integer value, String fieldName) {
        if (value == null || value <= 0) {
            throw new ValidationException(String.format("O campo %s deve ser maior que zero", fieldName));
        }
    }
}