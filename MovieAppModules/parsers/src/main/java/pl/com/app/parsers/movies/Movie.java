package pl.com.app.parsers.movies;


import pl.com.app.parsers.properties.GetGenreProperty;
import pl.com.app.validations.MovieValidationException;

public class Movie {
    private String title;
    private String typeOfMovie;
    private String director;

    public Movie() {
    }

    public Movie(String title, String typeOfMovie, String director) {
        setTitle(title);
        setTypeOfMovie(typeOfMovie);
        setDirector(director);
    }

    public Movie(Movie movie) {
        this(movie.getTitle(), movie.getTypeOfMovie(), movie.getDirector());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws MovieValidationException {
        if (title == null || !title.matches("[A-Z ]+")) {
            throw new MovieValidationException("title");
        }
        this.title = title;
    }

    public String getTypeOfMovie() {
        return typeOfMovie;
    }

    public void setTypeOfMovie(String typeOfMovie) throws MovieValidationException {
        GetGenreProperty getGenreProperty = new GetGenreProperty();
        if (typeOfMovie == null || !getGenreProperty.getPropValues().contains(typeOfMovie)) {
            throw new MovieValidationException("typeOfMovie");
        }
        this.typeOfMovie = typeOfMovie;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) throws MovieValidationException {
        if (director == null || !director.matches("[A-Z][a-z]+ [A-Z][a-z]+")) {
            throw new MovieValidationException("director");
        }
        this.director = director;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (title != null ? !title.equals(movie.title) : movie.title != null) return false;
        if (typeOfMovie != null ? !typeOfMovie.equals(movie.typeOfMovie) : movie.typeOfMovie != null) return false;
        return director != null ? director.equals(movie.director) : movie.director == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (typeOfMovie != null ? typeOfMovie.hashCode() : 0);
        result = 31 * result + (director != null ? director.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", typeOfMovie='" + typeOfMovie + '\'' +
                ", director='" + director + '\'' +
                '}';
    }
}
