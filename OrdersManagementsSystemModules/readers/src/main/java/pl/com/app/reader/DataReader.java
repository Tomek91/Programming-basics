package pl.com.app.reader;

import pl.com.app.models.enums.Category;
import pl.com.app.parsers.data.BigDecimalParser;
import pl.com.app.parsers.data.CategoryParser;
import pl.com.app.parsers.data.IntegerParser;
import pl.com.app.parsers.data.LocalDateParser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class DataReader {
    private static Scanner sc = new Scanner(System.in);

    public static void close() {
        sc.close();
    }

    public Integer getInteger() {
        return new IntegerParser().parse(sc.nextLine());
    }

    public BigDecimal getBigDecimal() {
        return new BigDecimalParser().parse(sc.nextLine());
    }

    public Category getCategory() {
        return new CategoryParser().parse(sc.nextLine());
    }

    public LocalDate getLocalDate() {
        return new LocalDateParser().parse(sc.nextLine());
    }

    public String getString() {
        return sc.nextLine();
    }

}
