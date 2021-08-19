package com.binance.dex.api.client.utils.converter;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumberUtil {

    public static final Integer SHIFT_DIGITS = 8;

    public static BigDecimal longToBigDecimal(Long value) {
        return longToBigDecimal(value, SHIFT_DIGITS);
    }

    public static String longToBigDecimalString(Long value) {
        return decimalFormat(longToBigDecimal(value, SHIFT_DIGITS));
    }

    public static BigDecimal longToBigDecimal(Long value, Integer shiftDigits) {
        if (null == value) {
            return null;
        }
        return BigDecimal.valueOf(value).movePointLeft(shiftDigits);
    }

    public static Long bigDecimalToLong(BigDecimal value) {
        return bigDecimalToLong(value, SHIFT_DIGITS);
    }

    private static Long bigDecimalToLong(BigDecimal value, Integer precision) {
        if (null == value) {
            return null;
        }
        return value.multiply(BigDecimal.TEN.pow(precision)).longValue();
    }

    public static String decimalFormat(BigDecimal value) {
        if (value == null) {
            return null;
        }
        return new DecimalFormat("0.00000000").format(value);
    }
}
