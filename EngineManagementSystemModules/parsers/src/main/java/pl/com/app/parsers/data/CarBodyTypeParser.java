package pl.com.app.parsers.data;

import pl.com.app.exceptions.MyException;
import pl.com.app.model.enums.CarBodyType;


public class CarBodyTypeParser implements Parser<CarBodyType> {
    @Override
    public CarBodyType parse(String line) {
        CarBodyType value;
        try {
            value = CarBodyType.valueOf(line);
        } catch (Exception e) {
            throw new MyException("CAR BODY TYPE PARSE EXCEPTION");
        }
        return value;
    }
}
