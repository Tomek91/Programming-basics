package pl.com.app.service;

import pl.com.app.connection.DbConnection;
import pl.com.app.models.TravelAgency;
import pl.com.app.models.Trip;
import pl.com.app.parsers.FileNames;
import pl.com.app.reader.DataReader;
import pl.com.app.service.utils.MenuItem;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MenuService {
    static final String PREFIX = "<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>";
    private DealService dealService;
    private DataLoaderService dataLoaderService = new DataLoaderService();

    public void dealsApp() {
        System.out.println("Witaj w symulatorze ofert podróży klientów !!!");
        System.out.println("Oto Twoje menu. Każda liczba odpowiada akcji, którą chcesz wykonać.\n" +
                "Pamiętaj, w każdej chwili możesz zakończyć aplikację naciskając 'q'.");

        // uzupelniam baze danych
        List<Trip> trips = dataLoaderService.loadTrips(FileNames.TRIPS);
        TripService tripService = new TripServiceImpl();
        tripService.deleteAll();
        tripService.addAll(trips);

        dealService = new DealService(FileNames.TRAVEL_AGENCY);
        RetrieveDataService.retrieveMenu(createMenu(), this::retrieveMainData);
    }

    private void retrieveMainData(String data) {
        switch (data) {
            case "1": {
                travelAgencyTheMostTrips();
                break;
            }
            case "2": {
                theMostEarnTravelAgency();
                break;
            }
            case "3": {
                theMostFrequencyCountry();
                break;
            }
            case "4": {
                avgPriceByTravelAgency();
                break;
            }
            case "5": {
                theLowestDiffPriceBetweenAvg();
                break;
            }
            case "6": {
                theMostAgencyInCountry();
                break;
            }
            case "7": {
                europeanTrips();
                break;
            }
            case "8": {
                numberOfPeopleInTrips();
                break;
            }
            case "9": {
                numberOfPeopleInMaxPriceTrip();
                break;
            }
            case "10": {
                showAllDeals();
                break;
            }
            case "q": {
                System.out.println("Koniec programu.");
                DataReader.close();
                DbConnection.getInstance().close();
                break;
            }
            default:
                System.out.println("Niepopoprawny kod menu. Spróbuj jeszcze raz.");
        }
    }

    private void showAllDeals() {
        DealService.showItems(dealService.getDeals());
    }

    private void numberOfPeopleInMaxPriceTrip() {
        Map<Integer, Trip> numberOfPeopleInMaxPriceTrip = dealService.numberOfPeopleInMaxPriceTrip();
        System.out.println("Number of people in max price trip ...");
        DealService.showItems(numberOfPeopleInMaxPriceTrip);
    }

    private void numberOfPeopleInTrips() {
        Map<Integer, List<Trip>> numberOfPeopleInTrips = dealService.numberOfPeopleInTrips();
        System.out.println("Number of people in trips ...");
        DealService.showItems(numberOfPeopleInTrips);
    }

    private void europeanTrips() {
        List<Trip> europeanTrips = dealService.europeanTrips(FileNames.COUNTRIES);
        System.out.println("European trips ...");
        europeanTrips.forEach(System.out::println);
    }

    private void theMostAgencyInCountry() {
        Map<String, TravelAgency> theMostAgencyInCountry = dealService.theMostAgencyInCountry();
        System.out.println("The most travel agency in country ...");
        DealService.showItems(theMostAgencyInCountry);
    }

    private void theLowestDiffPriceBetweenAvg() {
        Map<TravelAgency, Trip> theLowestDiffPriceBetweenAvg = dealService.theLowestDiffPriceBetweenAvg();
        System.out.println("The lowest difference between average and real price ...");
        DealService.showItems(theLowestDiffPriceBetweenAvg);
    }

    private void avgPriceByTravelAgency() {
        Map<TravelAgency, BigDecimal> avgPriceByTravelAgency = dealService.avgPriceByTravelAgency();
        System.out.println("Average trip price ...");
        DealService.showItems(avgPriceByTravelAgency);
    }

    private void theMostFrequencyCountry() {
        String theMostFrequencyCountry = dealService.theMostFrequencyCountry();
        System.out.println("The most frequency country ...");
        System.out.println(theMostFrequencyCountry);
    }

    private void theMostEarnTravelAgency() {
        TravelAgency theMostEarnTravelAgency = dealService.theMostEarnTravelAgency();
        System.out.println("The most earn ...");
        System.out.println(theMostEarnTravelAgency);
    }

    private void travelAgencyTheMostTrips() {
        List<TravelAgency> travelAgencyTheMostTrips = dealService.travelAgencyTheMostTrips();
        System.out.println("The most trips ...");
        travelAgencyTheMostTrips.forEach(System.out::println);
    }

    private List<MenuItem> createMenu() {
        return Arrays.asList(
                MenuItem.builder().code("1").name("nazwa biura podróży, które zorganizowało najwięcej wycieczek").build(),
                MenuItem.builder().code("2").name("nazwę biura podróży, które zarobiło najwięcej na wycieczkach zakładając, że na każdej wycieczce biuro podróży zarabia 10% wartości wycieczki obciążone podatkiem 19%").build(),
                MenuItem.builder().code("3").name("nazwa kraju, do którego najczęściej organizowano wycieczki").build(),
                MenuItem.builder().code("4").name("zestawienie, w którym dla każdego biura podróży podasz średnią cenę wycieczki").build(),
                MenuItem.builder().code("5").name("dane wycieczki, której cena ma wartość najmniej różniącą się od wyznaczonej średniej").build(),
                MenuItem.builder().code("6").name("zestawienie, w którym dla każdego kraju określisz biuro podróży, które najczęściej organizowało do tego kraju wycieczki").build(),
                MenuItem.builder().code("7").name("Na podstawie mapy sporządź listę, która przechowuje tylko te wycieczki, które odbywały się do krajów Europy. O tym, czy kraj jest w Europie dowiesz się z pliku tekstowego, który wcześniej przygotujesz i wypełnisz na potrzeby testów aplikacji nazwami kilku krajów europejskich.").build(),
                MenuItem.builder().code("8").name("Przygotuj mapę, w której kluczem jest ilość osób biorących udział w wycieczce, natomiast wartością kolekcja bez duplikatów wycieczek z taką ilością osób").build(),
                MenuItem.builder().code("9").name("Dla przygotowanej w poprzednim punkcie mapy wyznacz dla każdej ilości osób wycieczkę o najwyższej cenie. Zwróć tak przygotowane zestawienie w postaci mapy, gdzie kluczem jest ilość osób biorących udział w wycieczce, a wartością przyporządkowana im najdroższa wycieczka. Posortuj mapę malejąco według kosztu wycieczki przypadającego na jedną osobę.").build(),
                MenuItem.builder().code("10").name("pokaż mapę klientów z listami produktów").build(),
                MenuItem.builder().code("q").name("koniec aplikacji").build()
        );
    }
}
