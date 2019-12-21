package pl.com.app.parsers.data;

import pl.com.app.exceptions.MyException;
import pl.com.app.model.enums.Color;

public class EColorParser implements Parser<Color> {

    @Override
    public Color parse(String line) {
        Color value;
        try {
            value = Color.valueOf(line);
        } catch (Exception e) {
            throw new MyException("ECOLOR PARSE EXCEPTION");
        }
        return value;
    }
}
