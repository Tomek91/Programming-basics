package pl.com.app.parsers.data;

import pl.com.app.exceptions.MyException;
import pl.com.app.model.enums.TyreType;


public class TyreTypeParser implements Parser<TyreType> {
    @Override
    public TyreType parse(String line) {
        TyreType value;
        try {
            value = TyreType.valueOf(line);
        } catch (Exception e) {
            throw new MyException("TYRE TYPE PARSE EXCEPTION");
        }
        return value;
    }
}
