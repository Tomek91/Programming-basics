package pl.com.app.parsers.data;

import pl.com.app.exceptions.MyException;
import pl.com.app.model.enums.Colour;

public class ColourParser implements Parser<Colour> {

    @Override
    public Colour parse(String line) {
        Colour value;
        try {
            value = Colour.valueOf(line);
        } catch (Exception e) {
            throw new MyException("COLOUR PARSE EXCEPTION");
        }
        return value;
    }
}
