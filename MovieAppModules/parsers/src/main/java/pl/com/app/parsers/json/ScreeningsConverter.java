package pl.com.app.parsers.json;


import pl.com.app.parsers.movies.Screenings;

public class ScreeningsConverter extends JsonConverter<Screenings> {
    public ScreeningsConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
