package pl.com.app.parsers;

import pl.com.app.model.IntegerModel;

import java.util.List;

public class IntegerConverter extends JsonConverter<List<IntegerModel>> {
    public IntegerConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
