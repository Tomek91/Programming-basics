package pl.com.app.service;


import pl.com.app.model.CustomExceptionModel;
import pl.com.app.movie_fetch.FetchMovieRandom;
import pl.com.app.parsers.json.ExceptionConverter;
import pl.com.app.parsers.movies.Movie;
import pl.com.app.validations.CustomException;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

class MovieService {

    Movie fetchMovieRandom() {
        return new FetchMovieRandom().fetchMovie();
    }

    void showExceptionMap() {
        CustomException.showExceptionMap();
    }

    void saveException(String fileName) {
        if (fileName == null) {
            throw new CustomException("FILENAME IS NULL");
        }
        ExceptionConverter exceptionConverter = new ExceptionConverter(fileName);
        CustomExceptionModel model = new CustomExceptionModel();
        model.setExceptions(
                CustomException.getExceptionMap()
                        .entrySet()
                        .stream()
                        .collect(
                                Collectors.toMap(
                                        Map.Entry::getKey,
                                        e -> e.getValue()
                                                .stream()
                                                .map(CustomException::toString)
                                                .collect(Collectors.toList())
                                )
                        ));
        exceptionConverter.toJson(model);
    }

    String exceptionMaxDate() {
        return CustomException.getExceptionMap()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue()
                                .stream()
                                .max(Comparator.comparing(CustomException::getErrorDate))
                                .orElse(new CustomException("BRAK"))
                ))
                .entrySet()
                .stream()
                .max(Comparator.comparing(e -> e.getValue().getErrorDate()))
                .map(e -> e.getKey() + " " + e.getValue())
                .orElseThrow(() -> new CustomException("BRAK"));
    }

    String exceptionMinDate() {
        return CustomException.getExceptionMap()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue()
                                .stream()
                                .min(Comparator.comparing(CustomException::getErrorDate))
                                .orElse(new CustomException("BRAK"))
                ))
                .entrySet()
                .stream()
                .min(Comparator.comparing(e -> e.getValue().getErrorDate()))
                .map(e -> e.getKey() + " " + e.getValue())
                .orElseThrow(() -> new CustomException("BRAK"));
    }

    String theMostCommonException() {
        return CustomException.getExceptionMap()
                .entrySet()
                .stream()
                .max(Comparator.comparing(x -> x.getValue().size()))
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new CustomException("BRAK"));
    }

    void clearException() {
        CustomException.getExceptionMap().clear();
    }
}
