package pl.com.app.reader;

import pl.com.app.data.BigDecimalParser;
import pl.com.app.data.LocalDateParser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class DataReader {
    private static Scanner sc = new Scanner(System.in);

    public static void close() {
        sc.close();
    }

    public BigDecimal getBigDecimal(){
        return new BigDecimalParser().parse(sc.nextLine());
    }

    public LocalDate getLocalDate(){
        return new LocalDateParser().parse(sc.nextLine());
    }

    public String getString(){
        return sc.nextLine();
    }
}
