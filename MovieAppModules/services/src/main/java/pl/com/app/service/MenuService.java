package pl.com.app.service;

import pl.com.app.parsers.json.FileNames;
import pl.com.app.parsers.movies.Movie;
import pl.com.app.parsers.movies.Programme;
import pl.com.app.parsers.movies.Screening;
import pl.com.app.reader.DataReader;
import pl.com.app.service.utils.MenuItem;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class MenuService {
    static final String PREFIX = "<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>";
    private MovieService movieService;
    private DataLoaderService dataLoaderService;

    public void movieApp() {
        System.out.println("Witaj w symulatorze repertuaru seansów !!!");
        System.out.println("Oto Twoje menu. Każda liczba odpowiada akcji, którą chcesz wykonać.\n" +
                "Pamiętaj, w każdej chwili możesz zakończyć aplikację naciskając 'q'.");

        movieService = new MovieService();
        dataLoaderService = new DataLoaderService();
        RetrieveDataService.retrieveMenu(createMenu(), this::retrieveMainData);
    }

    private void retrieveMainData(String data) {
        switch (data) {
            case "1": {
                fetchMovieRandom();
                break;
            }
            case "2": {
                fetchMovieFile();
                break;
            }
            case "3": {
                loadScreenings();
                break;
            }
            case "4": {
                generateProgramme();
                break;
            }
            case "5": {
                exceptionMinDate();
                break;
            }
            case "6": {
                exceptionMaxDate();
                break;
            }
            case "7": {
                theMostCommonException();
                break;
            }
            case "8": {
                clearException();
                break;
            }
            case "9": {
                showExceptionMap();
                break;
            }
            case "10": {
                saveExceptions();
                break;
            }
            case "q": {
                System.out.println("Koniec programu.");
                DataReader.close();
                break;
            }
            default:
                System.out.println("Niepopoprawny kod menu. Spróbuj jeszcze raz.");
        }
    }

    private void saveExceptions() {
        movieService.saveException(FileNames.EXCEPTIONS);
        System.out.println("saved");
    }

    private void showExceptionMap() {
        movieService.showExceptionMap();
    }

    private void clearException() {
        System.out.println("clearException");
        movieService.clearException();
    }

    private void theMostCommonException() {
        System.out.println("theMostCommonException");
        String theMostCommonException = movieService.theMostCommonException();
        System.out.println(theMostCommonException);
    }

    private void exceptionMaxDate() {
        System.out.println("exceptionMaxDate");
        String exceptionMaxDate = movieService.exceptionMaxDate();
        System.out.println(exceptionMaxDate);
    }

    private void exceptionMinDate() {
        System.out.println("exceptionMinDate");
        String exceptionMinDate = movieService.exceptionMinDate();
        System.out.println(exceptionMinDate);
    }

    private void generateProgramme() {
        Programme programme = dataLoaderService.generateProgramme(
                FileNames.SCREENING,
                LocalDate.of(2018, 4, 1),
                LocalDate.of(2018, 4, 22)
        );
        programme
                .getRepMap()
                .forEach((k, v) -> System.out.println(k + " " + v));
    }

    private void loadScreenings() {
        List<Screening> loadScreenings = dataLoaderService.loadScreenings(FileNames.SCREENING);
        System.out.println("loadScreenings");
        loadScreenings.forEach(System.out::println);
    }

    private void fetchMovieFile() {
        List<Movie> fetchMovieFile = dataLoaderService.fetchMovieFile(FileNames.MOVIES);
        System.out.println("fetchMovieFile");
        fetchMovieFile.forEach(System.out::println);
    }

    private void fetchMovieRandom() {
        Movie movie = movieService.fetchMovieRandom();
        System.out.println("fetchMovieRandom " + movie);
    }

    private List<MenuItem> createMenu() {
        return Arrays.asList(
                MenuItem.builder().code("1").name("wylosuj nowy film").build(),
                MenuItem.builder().code("2").name("pobierz i wypisz filmy z pliku json").build(),
                MenuItem.builder().code("3").name("pobierz seanse z pliku json").build(),
                MenuItem.builder().code("4").name("utwórz i wypisz repertuar").build(),
                MenuItem.builder().code("5").name("pokaż dane wyjątku (nazwa + komunikat), który wystąpił najwcześniej").build(),
                MenuItem.builder().code("6").name("pokaż dane wyjątku (nazwa + komunikat), który wystąpił najpóźniej").build(),
                MenuItem.builder().code("7").name("zwracająca nazwę klasy wyjątku, który wystąpił najczęściej").build(),
                MenuItem.builder().code("8").name("usuń mapę z historią wyjątków").build(),
                MenuItem.builder().code("9").name("wypisz mapę wyjątków").build(),
                MenuItem.builder().code("10").name("zapisz wyjątki do pliku").build(),
                MenuItem.builder().code("q").name("koniec aplikacji").build()
        );
    }
}
