package pl.com.app.parsers;

import pl.com.app.models.Trip;

import java.util.List;

public class TripsConverter extends JsonConverter<List<Trip>> {

    public TripsConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
