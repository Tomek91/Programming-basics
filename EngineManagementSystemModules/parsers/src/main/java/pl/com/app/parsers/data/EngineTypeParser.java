package pl.com.app.parsers.data;

import pl.com.app.exceptions.MyException;
import pl.com.app.model.enums.EngineType;

public class EngineTypeParser implements Parser<EngineType> {
    @Override
    public EngineType parse(String line) {
        EngineType value;
        try {
            value = EngineType.valueOf(line);
        } catch (Exception e) {
            throw new MyException("ENGINE TYPE PARSE EXCEPTION");
        }
        return value;
    }
}
