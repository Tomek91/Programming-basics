package pl.com.app.service;

import pl.com.app.model.CurrencyModel;
import pl.com.app.model.Rate;
import pl.com.app.reader.DataReader;
import pl.com.app.service.utils.MenuItem;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MenuService {
    static final String PREFIX = "<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>";
    private DataReader dataReader = new DataReader();
    private RatesTasksService ratesTasksService;

    public void currenciesRatesAppSimulator() {
        System.out.println("Witaj w symulatorze zarządzania walutami !!!");
        System.out.println("Oto Twoje menu. Każda liczba odpowiada akcji, którą chcesz wykonać.\n" +
                "Pamiętaj, w każdej chwili możesz zakończyć aplikację naciskając 'q'.");

        ratesTasksService = new RatesTasksService();
        LocalDate dateFromUser = RatesTasksService.dateFromUser();
        RetrieveDataService.retrieveMenu(createMenu(), x -> retrieveMainData(x, dateFromUser));
    }

    private void retrieveMainData(String data, LocalDate dateFromUser) {
        switch (data) {
            case "1": {
                generateCurrencyModel(dateFromUser);
                return;
            }
            case "2": {
                threeMaxGrowth(dateFromUser);
                break;
            }
            case "3": {
                growthPercentCurrency(dateFromUser);
                break;
            }
            case "4": {
                startWithUserExpression(dateFromUser);
                break;
            }
            case "5": {
                avgGrowthIn10Price(dateFromUser);
                break;
            }
            case "6": {
                maxGrowthAndAvgRate(dateFromUser);
                break;
            }
            case "7": {
                dontGrowth(dateFromUser);
                break;
            }
            case "8": {
                theBestInvestment(dateFromUser);
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

    private void theBestInvestment(LocalDate dateFromUser) {
        System.out.println("------------------------------------------------------------------------");
        List<String> theBestInvestment = ratesTasksService.theBestInvestment(dateFromUser);
        System.out.println("w co inwestowac???");
        theBestInvestment.forEach(System.out::println);
    }

    private void dontGrowth(LocalDate dateFromUser) {
        String dontGrowth = ratesTasksService.dontGrowth(dateFromUser);
        System.out.println("symbole walut, ktore w podanym dniu nie zanotowaly wzrostu");
        System.out.println(dontGrowth);
    }

    private void maxGrowthAndAvgRate(LocalDate dateFromUser) {
        List<String> maxGrowthAndAvgRate = ratesTasksService.maxGrowthAndAvgRate(dateFromUser);
        System.out.println("najwiekszym wzroscie w podanym przez usera dniu i podac jej sredni kurs w ostatnich 10 dniach od podanej daty");
        maxGrowthAndAvgRate.forEach(System.out::println);
    }

    private void avgGrowthIn10Price(LocalDate dateFromUser) {
        Map<BigDecimal, List<String>> avgGrowthIn10Price = ratesTasksService.avgGrowthIn10Price(dateFromUser);
        System.out.println("symbole walut wedlug sredniego wzrostu z ostatnich 10 notowan");
        avgGrowthIn10Price.forEach((k, v) -> System.out.println(k + " " + v));
    }

    private void startWithUserExpression(LocalDate dateFromUser) {
        System.out.println("podaj poczatkowe litery nazwy walut");
        final String currencyStartWith = dataReader.getString();
        System.out.println("symbole rozpoczynajacym sie od wyrazenia podanego przez usera");
        List<Rate> startWithUserExpression = ratesTasksService.startWithUserExpression(dateFromUser, currencyStartWith);
        if (startWithUserExpression.isEmpty()) {
            System.out.println("PUSTO");
        }
        startWithUserExpression.forEach(System.out::println);
    }

    private void growthPercentCurrency(LocalDate dateFromUser) {
        System.out.println("podaj 2 przedzialy spadku procentowego");
        BigDecimal min = dataReader.getBigDecimal();
        BigDecimal max = dataReader.getBigDecimal();
        String growthPercentCurrency = ratesTasksService.growthPercentCurrency(dateFromUser, min, max);
        System.out.println("symbole walut, ktore w danym dniu zanotowaly spadek procentowy");
        System.out.println(growthPercentCurrency);
    }

    private void threeMaxGrowth(LocalDate dateFromUser) {
        List<String> threeMaxGrowth = ratesTasksService.threeMaxGrowth(dateFromUser);
        System.out.println("3 najwieksze wzrosty w dniu " + dateFromUser + " podanym przez usera w stosunku do dnia poprzedniego");
        threeMaxGrowth.forEach(System.out::println);
    }

    private void generateCurrencyModel(LocalDate dateFromUser) {
        CurrencyModel curModel = LoadCurrencyService.generateCurrencyModel(dateFromUser.toString());
        System.out.println(curModel.toString());
    }

    private List<MenuItem> createMenu() {
        return Arrays.asList(
                MenuItem.builder().code("1").name("pobierz waluty z dnia podanego przez usera").build(),
                MenuItem.builder().code("2").name("wybrac 3 najwieksze wzrosty w dniu podanym przez usera w stosunku do dnia poprzedniego").build(),
                MenuItem.builder().code("3").name("wyznaczyc wszystkie symbole walut, ktore w danym dniu zanotowaly spadek procentowy o wartosciach w przedziale podanym przez usera").build(),
                MenuItem.builder().code("4").name("zwrocic zestawienie walut o symbolu rozpoczynajacym sie od wyrazenia podanego przez usera").build(),
                MenuItem.builder().code("5").name("pogrupowac symbole walut wedlug sredniego wzrostu z ostatnich 10 notowan").build(),
                MenuItem.builder().code("6").name("wybrac walute o najwiekszym wzroscie w podanym przez usera dniu i podac jej sredni kurs w ostatnich 10 dniach od podanej daty").build(),
                MenuItem.builder().code("7").name("wypisac po przecinku te symbole walut, ktore w podanym dniu nie zanotowaly wzrostu").build(),
                MenuItem.builder().code("8").name("opracowac algorytm, ktory podpowie userowi w co inwestowac. Zakladamy ze inwestycja oplaca sie jezeli w ostatnich 10 dniach waluta miala spadek co najwyzej 2 razy").build(),
                MenuItem.builder().code("q").name("koniec aplikacji").build()
        );
    }
}
