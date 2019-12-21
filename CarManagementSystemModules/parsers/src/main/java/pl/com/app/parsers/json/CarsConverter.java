package pl.com.app.parsers.json;

import pl.com.app.model.Cars;

public class CarsConverter extends JsonConverter<Cars> {
    public CarsConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
