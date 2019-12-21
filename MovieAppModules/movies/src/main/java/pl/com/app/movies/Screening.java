package pl.com.app.movies;

import pl.com.app.parsers.properties.GetScreeningProperty;
import pl.com.app.validations.ScreeningValidationException;

import java.util.Map;

public class Screening {
    private Movie movie;
    private int ticketPrice;
    private int filmDuration;
    private int numberOfHall;

    public Screening(Movie movie, int ticketPrice, int filmDuration, int numberOfHall) {
        Map<String, String> propValues = new GetScreeningProperty().getPropValues();
        setMovie(movie);
        setTicketPrice(ticketPrice, propValues.get("CENA_MIN"), propValues.get("CENA_MAX"));
        setFilmDuration(filmDuration, propValues.get("CZAS_FILMU1"), propValues.get("CZAS_FILMU2"), propValues.get("CZAS_FILMU3"));
        setNumberOfHall(numberOfHall, propValues.get("SALA_MIN"), propValues.get("SALA_MAX"));
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice, String priceMin, String priceMax) throws ScreeningValidationException {
        if (ticketPrice < Integer.valueOf(priceMin) || ticketPrice > Integer.valueOf(priceMax)) {
            throw new ScreeningValidationException("ticketPrice");
        }
        this.ticketPrice = ticketPrice;
    }

    public int getFilmDuration() {
        return filmDuration;
    }

    public void setFilmDuration(int filmDuration, String timeMovie1, String timeMovie2, String timeMovie3) throws ScreeningValidationException {
        if (filmDuration != Integer.valueOf(timeMovie1) &&
                filmDuration != Integer.valueOf(timeMovie2) &&
                filmDuration != Integer.valueOf(timeMovie3)) {
            throw new ScreeningValidationException("filmDuration");
        }
        this.filmDuration = filmDuration;
    }

    public int getNumberOfHall() {
        return numberOfHall;
    }

    public void setNumberOfHall(int numberOfHall, String hallMin, String hallMax) throws ScreeningValidationException {
        if (numberOfHall < Integer.valueOf(hallMin) || numberOfHall > Integer.valueOf(hallMax)) {
            throw new ScreeningValidationException("numberOfHall");
        }
        this.numberOfHall = numberOfHall;
    }

    @Override
    public String toString() {
        return "Screening{" +
                "movie=" + movie +
                ", ticketPrice=" + ticketPrice +
                ", filmDuration=" + filmDuration +
                ", numberOfHall=" + numberOfHall +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Screening screening = (Screening) o;

        if (ticketPrice != screening.ticketPrice) return false;
        if (filmDuration != screening.filmDuration) return false;
        if (numberOfHall != screening.numberOfHall) return false;
        return movie != null ? movie.equals(screening.movie) : screening.movie == null;
    }

    @Override
    public int hashCode() {
        int result = movie != null ? movie.hashCode() : 0;
        result = 31 * result + ticketPrice;
        result = 31 * result + filmDuration;
        result = 31 * result + numberOfHall;
        return result;
    }
}


