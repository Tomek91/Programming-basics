package pl.com.app.parsers.data;

import pl.com.app.exceptions.MyException;
import pl.com.app.model.enums.Component;

public class ComponentParser implements Parser<Component> {
    @Override
    public Component parse(String line) {
        Component value;
        try {
            value = Component.valueOf(line);
        } catch (Exception e) {
            throw new MyException("COMPONENT PARSE EXCEPTION");
        }
        return value;
    }
}
