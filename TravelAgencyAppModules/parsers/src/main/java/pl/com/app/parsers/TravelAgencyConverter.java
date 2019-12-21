package pl.com.app.parsers;

import pl.com.app.models.TravelAgency;

import java.util.List;

public class TravelAgencyConverter extends JsonConverter<List<TravelAgency>> {

    public TravelAgencyConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
