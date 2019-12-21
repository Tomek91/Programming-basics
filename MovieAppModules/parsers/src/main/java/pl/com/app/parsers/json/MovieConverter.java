package pl.com.app.parsers.json;


import pl.com.app.parsers.movies.Movie;

import java.util.List;

public class MovieConverter extends JsonConverter<List<Movie>> {
    public MovieConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
