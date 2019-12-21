package pl.com.app.service;

import pl.com.app.exceptions.MyException;
import pl.com.app.model.Car;
import pl.com.app.model.enums.Color;
import pl.com.app.model.enums.Order;
import pl.com.app.model.enums.SortBy;
import pl.com.app.parsers.json.FileNames;
import pl.com.app.reader.DataReader;
import pl.com.app.service.utils.MenuItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MenuService {
    static final String PREFIX = "<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>";
    private DataReader dataReader = new DataReader();
    private CarsService carsService;

    public void carsManagementSimulator() {
        System.out.println("Witaj w symulatorze zarządzania samochodami !!!");
        System.out.println("Oto Twoje menu. Każda liczba odpowiada akcji, którą chcesz wykonać.\n" +
                "Pamiętaj, w każdej chwili możesz zakończyć aplikację naciskając 'q'.");

        carsService = getCarServiceInstance();
        RetrieveDataService.retrieveMenu(createMenu(), this::retrieveMainData);
    }

    private CarsService getCarServiceInstance(){
        return new CarsService(FileNames.CAR_MANAGEMENT);
    }

    private void retrieveMainData(String data) {
        switch (data) {
            case "1": {
                try {
                    addNewCar();
                    break;
                } catch (MyException e) {
                    System.err.println(e.toString());
                    return;
                }
            }
            case "2": {
                sortCustom();
                break;
            }
            case "3": {
                mileageMoreThan();
                break;
            }
            case "4": {
                colorIntegerMap();
                break;
            }
            case "5": {
                maxPriceModel();
                break;
            }
            case "6": {
                statistics();
                break;
            }
            case "7": {
                maxPriceCar();
                break;
            }
            case "8": {
                componentsAlphabeticSort();
                break;
            }
            case "9": {
                componentsMap();
                break;
            }
            case "10": {
                rangePrice();
                break;
            }
            case "11":{
                loadDataFromJson();
                break;
            }
            case "12":{
                showAllCars();
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

    private void showAllCars() {
        carsService.getCars().forEach(System.out::println);
    }

    private void loadDataFromJson() {
        CarsService carsServiceJson = getCarServiceInstance();
        carsService.getCars().clear();
        carsService.getCars().addAll(carsServiceJson.getCars());
        System.out.println("Samochody zostały poprawnie załadowane z pliku json.");
    }

    private void rangePrice() {
        System.out.println("Podaj cene min");
        BigDecimal minPrice = dataReader.getBigDecimal();
        System.out.println("Podaj cene max");
        BigDecimal maxPrice = dataReader.getBigDecimal();
        System.out.println("rangePriceCars");
        List<Car> rangePriceCars = carsService.rangePrice(minPrice, maxPrice);
        rangePriceCars.forEach(System.out::println);
    }

    private void componentsMap() {
        System.out.println("componentsMap");
        Map<String, List<Car>> componentsMap = carsService.componentsMap();
        componentsMap.forEach((k, v) -> System.out.println(k + " " + v));
    }

    private void componentsAlphabeticSort() {
        System.out.println("componentsAlphabeticSort");
        List<Car> componentsAlphabeticSort = carsService.componentsAlphabeticSort();
        componentsAlphabeticSort.forEach(System.out::println);
    }

    private void maxPriceCar() {
        System.out.println("maxPriceCars");
        List<Car> maxPriceCars = carsService.maxPriceCar();
        maxPriceCars.forEach(System.out::println);
    }

    private void statistics() {
        System.out.println("statistics");
        carsService.statistics();
    }

    private void maxPriceModel() {
        System.out.println("maxPriceModel");
        Map<String, Car> maxPriceModel = carsService.maxPriceModel();
        maxPriceModel.forEach((k, v) -> System.out.println(k + " " + v));
    }

    private void colorIntegerMap() {
        System.out.println("eColorIntegerMap");
        Map<Color, Long> eColorIntegerMap = carsService.eColorIntegerMap();
        eColorIntegerMap.forEach((k, v) -> System.out.println(k + " " + v));
    }

    private void mileageMoreThan() {
        System.out.println("Podaj przebieg");
        Integer mileageMark = dataReader.getInteger();
        List<Car> mileageMoreThan = carsService.mileageMoreThan(mileageMark);
        System.out.println("mileageMoreThan");
        mileageMoreThan.forEach(System.out::println);
    }

    private void sortCustom() {
        String dataOrder = RetrieveDataService.retrieveMenuWithReturnValue(createSortOrderMenu(), this::retrieveOrderData);
        if (dataOrder != null && !dataOrder.equalsIgnoreCase("q")) {
            RetrieveDataService.retrieveMenu(createSortMenu(), x -> retrieveSortData(x, dataOrder));
        }
    }

    private void addNewCar() {
        Car carToAdd = new Car();
        System.out.println("Podaj model");
        carToAdd.setModel(dataReader.getString());
        System.out.println("Podaj cene");
        carToAdd.setPrice(dataReader.getBigDecimal());
        System.out.println("Podaj kolor");
        carToAdd.setColor(dataReader.getColor());
        System.out.println("Podaj liczbę przejechanych kilometrów");
        carToAdd.setMileage(dataReader.getInteger());
        System.out.println("Podaj komponenty - q kończy dodawanie");
        List<String> components = new ArrayList<>();
        String dataComponent = dataReader.getString();
        while (!dataComponent.equalsIgnoreCase("q")) {
            components.add(dataComponent);
            dataComponent = dataReader.getString();
        }
        carToAdd.setComponents(components);
        carsService.getCars().add(carToAdd);
    }

    private void retrieveOrderData(String data) {
        switch (data) {
            case "r": {
                System.out.println("r");
                break;
            }
            case "m": {
                System.out.println("m");
                break;
            }
            case "q": {
                System.out.println("wyjdz.");
                break;
            }
            default:
                System.out.println("Niepopoprawny kod menu. Spróbuj jeszcze raz.");
        }
    }

    private void retrieveSortData(String data, String orderData) {
        Order order = orderData.equals("r") ? Order.REVERSE : Order.NATURAL;
        switch (data) {
            case "u": {
                List<Car> carList = carsService.customSort(SortBy.COLOUR, order);
                System.out.println("SortBy.COLOUR");
                carList.forEach(System.out::println);
                break;
            }
            case "x": {
                List<Car> carList = carsService.customSort(SortBy.PRICE, order);
                System.out.println("SortBy.PRICE");
                carList.forEach(System.out::println);
                break;
            }
            case "y": {
                List<Car> carList = carsService.customSort(SortBy.MILEAGE, order);
                System.out.println("SortBy.MILEAGE");
                carList.forEach(System.out::println);
                break;
            }
            case "z": {
                List<Car> carList = carsService.customSort(SortBy.MODEL, order);
                System.out.println("SortBy.MODEL");
                carList.forEach(System.out::println);
                break;
            }
            case "q": {
                System.out.println("wyjdz");
                break;
            }
            default:
                System.out.println("Niepopoprawny kod menu. Spróbuj jeszcze raz.");
        }
    }

    private List<MenuItem> createSortMenu() {
        return Arrays.asList(
                MenuItem.builder().code("u").name("kolor").build(),
                MenuItem.builder().code("x").name("cena").build(),
                MenuItem.builder().code("y").name("liczba przejechanych kilometrów").build(),
                MenuItem.builder().code("z").name("model").build(),
                MenuItem.builder().code("q").name("wyjdz").build()
        );
    }

    private List<MenuItem> createSortOrderMenu() {
        return Arrays.asList(
                MenuItem.builder().code("r").name("rosnąco").build(),
                MenuItem.builder().code("m").name("malejąco").build(),
                MenuItem.builder().code("q").name("wyjdz").build()
        );
    }

    private List<MenuItem> createMenu() {
        return Arrays.asList(
                MenuItem.builder().code("1").name("dodaj samochód").build(),
                MenuItem.builder().code("2").name("sortowanie według kryteriów do wyboru").build(),
                MenuItem.builder().code("3").name("samochody o przebiegu większym niż podany").build(),
                MenuItem.builder().code("4").name("mapa kolorów samochodów i ilości samochdów mających ten kolor").build(),
                MenuItem.builder().code("5").name("mapa modeli i najdroższych samochodów w tym modelu").build(),
                MenuItem.builder().code("6").name("statystyka").build(),
                MenuItem.builder().code("7").name("lista najdroższych samochodów").build(),
                MenuItem.builder().code("8").name("samochody z posortowaną alfabetycznie kolekcją komponentów").build(),
                MenuItem.builder().code("9").name("mapa komponentów i kolekcja samochdów mających ten komponent").build(),
                MenuItem.builder().code("10").name("kolekcja samochodów z przedziału cenowego").build(),
                MenuItem.builder().code("11").name("zaladuj samochody z pliku json").build(),
                MenuItem.builder().code("12").name("pokaz wszystkie samochody").build(),
                MenuItem.builder().code("q").name("koniec aplikacji").build()
        );
    }
}
