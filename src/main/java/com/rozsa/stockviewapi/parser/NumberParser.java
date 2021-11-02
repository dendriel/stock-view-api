package com.rozsa.stockviewapi.parser;

import lombok.SneakyThrows;

import java.text.NumberFormat;
import java.util.Locale;

public class NumberParser {

    @SneakyThrows
    public static Double parseCommaSeparatedDouble(String value) {
        NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
        Number number = format.parse(value);
        return number.doubleValue();
    }
}
