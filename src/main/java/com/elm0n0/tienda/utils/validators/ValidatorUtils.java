package com.elm0n0.tienda.utils.validators;

import java.util.regex.Pattern;

public class ValidatorUtils {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    private static final String PHONE_REGEX = "^[0-9]{7,15}$"; // Ajusta según tu país

    public static boolean isValidEmail(String input) {
        return Pattern.compile(EMAIL_REGEX).matcher(input).matches();
    }

    public static boolean isValidPhone(String input) {
        return Pattern.compile(PHONE_REGEX).matcher(input).matches();
    }
}
