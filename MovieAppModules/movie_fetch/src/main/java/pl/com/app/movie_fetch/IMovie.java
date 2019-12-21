package pl.com.app.movie_fetch;


import pl.com.app.parsers.movies.Movie;

@FunctionalInterface
public interface IMovie {
    Movie fetchMovie();
}
