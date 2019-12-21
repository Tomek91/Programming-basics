package pl.com.app.parsers.movies;


import pl.com.app.parsers.json.ScreeningsConverter;
import pl.com.app.validations.CustomException;

import java.util.ArrayList;
import java.util.List;

public class Screenings {
    private final List<Screening> screenings = new ArrayList<>();

    public Screenings() {
    }

    public Screenings(String fileName) {
        ScreeningsConverter screeningsConverter = new ScreeningsConverter(fileName);
        screenings.addAll(retrieveScreenings(
                screeningsConverter
                        .fromJson()
                        .orElseThrow(() -> new CustomException("SCREENING PARSER EXCEPTION")).getScreenings()
        ));
    }

    public List<Screening> getScreenings() {
        return screenings;
    }

    private List<Screening> retrieveScreenings(List<Screening> screeningList) {
        List<Screening> screenings = new ArrayList<>();
        for (Screening screening : screeningList) {
            try {
                screenings.add(new Screening(screening.getMovie(), screening.getTicketPrice(), screening.getFilmDuration(), screening.getNumberOfHall()));
            } catch (CustomException e) {
                CustomException.addPairToExceptionMap("WALIDACJA SEANSU", e);
            }
        }
        return screenings;
    }
}
