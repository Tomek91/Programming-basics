package pl.com.app.movie_fetch;

import pl.com.app.parsers.movies.Movie;
import pl.com.app.validations.CustomException;

import java.util.Random;

public class FetchMovieRandom implements IMovie {
    public static final String[] TITLES = {"RAZ DWA TRZY", "RAMBO", "ROCKY", "VIPEr", "rYDWAN", "997", "KOMISARZ ALEKS"};
    public static final String[] GENRES = {"komedia", "wojenny", "sensacyjny", "bezGatunku", "nieznany", "horror"};
    public static final String[] DIRECTORS = {"Krzysiek Jagoda", "Aneta Kowal", "Jurek Trzmiel", "Waldek Koniec", "patryk rak", "Syrena dominguez"};

    @Override
    public Movie fetchMovie() {
        Movie movie = null;
        while (movie == null) {
            try {
                movie = new Movie(
                        TITLES[new Random().nextInt(TITLES.length)],
                        GENRES[new Random().nextInt(GENRES.length)],
                        DIRECTORS[new Random().nextInt(DIRECTORS.length)]
                );
            } catch (CustomException e) {
                CustomException.addPairToExceptionMap("WALIDACJA FILM", e);
            }
        }
        return movie;
    }
}

