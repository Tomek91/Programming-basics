package pl.com.app.data;

import pl.com.app.exceptions.MyException;

public class IntegerParser implements Parser<Integer> {
    @Override
    public Integer parse(String line) {
        Integer value;
        try {
            value = Integer.valueOf(line);
        } catch (Exception e) {
            throw new MyException("INTEGER PARSE EXCEPTION");
        }
        return value;
    }
}
