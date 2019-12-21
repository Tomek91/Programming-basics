package pl.com.app.parsers.data;

import pl.com.app.exceptions.MyException;

import java.math.BigDecimal;

public class BigDecimalParser implements Parser<BigDecimal> {

    @Override
    public BigDecimal parse(String line) {
        BigDecimal value;
        try {
            value = new BigDecimal(line);
        } catch (Exception e) {
            throw new MyException("BIG DECIMAL PARSE EXCEPTION");
        }
        return value;
    }
}
