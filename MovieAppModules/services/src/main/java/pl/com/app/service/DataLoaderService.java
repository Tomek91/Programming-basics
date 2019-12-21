package pl.com.app.service;


import pl.com.app.movie_fetch.FetchMovieFile;
import pl.com.app.parsers.movies.Movie;
import pl.com.app.parsers.movies.Programme;
import pl.com.app.parsers.movies.Screening;
import pl.com.app.parsers.movies.Screenings;
import pl.com.app.validations.CustomException;

import java.time.LocalDate;
import java.util.List;

class DataLoaderService {

    List<Movie> fetchMovieFile(String fileName) {
        if (fileName == null) {
            throw new CustomException("FILENAME IS NULL");
        }
        return new FetchMovieFile(fileName).getMovies();
    }

    List<Screening> loadScreenings(String fileName) {
        if (fileName == null) {
            throw new CustomException("FILENAME IS NULL");
        }
        return new Screenings(fileName).getScreenings();
    }

    Programme generateProgramme(String fileName, LocalDate from, LocalDate to) {
        if (fileName == null || from == null || to == null) {
            throw new CustomException("ARGS ARE NULL");
        }
        return new Programme(fileName, from, to);
    }
}