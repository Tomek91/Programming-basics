package pl.com.app.parsers.json;

import pl.com.app.model.Car;


public class CarConverter extends JsonConverter<Car> {
    public CarConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
