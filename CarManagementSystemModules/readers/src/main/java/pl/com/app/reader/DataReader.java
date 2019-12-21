package pl.com.app.reader;

import pl.com.app.model.enums.Color;
import pl.com.app.parsers.data.BigDecimalParser;
import pl.com.app.parsers.data.EColorParser;
import pl.com.app.parsers.data.IntegerParser;

import java.math.BigDecimal;
import java.util.Scanner;

public class DataReader {
    private static Scanner sc = new Scanner(System.in);

    public static void close() {
        sc.close();
    }

    public Integer getInteger(){
        return new IntegerParser().parse(sc.nextLine());
    }

    public BigDecimal getBigDecimal(){
        return new BigDecimalParser().parse(sc.nextLine());
    }

    public String getString(){
        return sc.nextLine();
    }

    public Color getColor(){
        return new EColorParser().parse(sc.nextLine());
    }
}
