package pl.com.app.readers;

import pl.com.app.model.enums.*;
import pl.com.app.parsers.data.*;

import java.math.BigDecimal;
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

    public Colour getColour() {
        return new ColourParser().parse(sc.nextLine());
    }

    public EngineType getEngineType() {
        return new EngineTypeParser().parse(sc.nextLine());
    }

    public CarBodyType getCarBodyType() {
        return new CarBodyTypeParser().parse(sc.nextLine());
    }

    public TyreType getTyreType() {
        return new TyreTypeParser().parse(sc.nextLine());
    }

    public Component getComponent() {
        return new ComponentParser().parse(sc.nextLine());
    }

    public String getString() {
        return sc.nextLine();
    }

}
