package pl.com.app.movie_fetch;


import pl.com.app.parsers.json.MovieConverter;
import pl.com.app.parsers.movies.Movie;
import pl.com.app.validations.CustomException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FetchMovieFile implements IMovie {
    private List<Movie> movies = new ArrayList<>();

    public FetchMovieFile(String fileName) {
        MovieConverter movieConverter = new MovieConverter(fileName);
        movies = retrieveMovies(movieConverter.fromJson().orElseThrow(() -> new CustomException("MOVIES PARSER EXCEPTION")));
    }

    public FetchMovieFile(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    private List<Movie> retrieveMovies(List<Movie> movieList) {
        List<Movie> movies = new ArrayList<>();
        for (Movie m : movieList) {
            try {
                movies.add(new Movie(m));
            } catch (CustomException e) {
                CustomException.addPairToExceptionMap("WALIDACJA FILM", e);
            }
        }
        return movies;
    }

    @Override
    public Movie fetchMovie() {
        return movies.get(new Random().nextInt(movies.size()));
    }
}
