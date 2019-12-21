package pl.com.app.parsers;

import pl.com.app.models.Country;

import java.util.List;

public class CountriesConverter extends JsonConverter<List<Country>> {

    public CountriesConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
