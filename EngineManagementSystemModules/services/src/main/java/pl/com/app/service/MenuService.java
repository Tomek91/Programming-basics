package pl.com.app.service;


import pl.com.app.readers.DataReader;
import pl.com.app.exceptions.MyException;
import pl.com.app.model.Car;
import pl.com.app.model.CarBody;
import pl.com.app.model.Engine;
import pl.com.app.model.Wheel;
import pl.com.app.model.enums.*;
import pl.com.app.parsers.data.ComponentParser;
import pl.com.app.parsers.data.Parser;
import pl.com.app.parsers.json.FileNames;
import pl.com.app.service.utils.MenuItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MenuService {
    static final String PREFIX = "<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>";
    private DataReader dataReader = new DataReader();
    private CarsService carsService;

    public void engineManagementSimulator() {
        System.out.println("Witaj w symulatorze zarządzania silnikami samochodów !!!");
        System.out.println("Oto Twoje menu. Każda liczba odpowiada akcji, którą chcesz wykonać.\n" +
                "Pamiętaj, w każdej chwili możesz zakończyć aplikację naciskając 'q'.");

        this.carsService = getCarServiceInstance();
        RetrieveDataService.retrieveMenu(createMenu(), this::retrieveMainData);
    }

    private CarsService getCarServiceInstance(){
        return new CarsService(Arrays.asList(FileNames.CAR1, FileNames.CAR2, FileNames.CAR3, FileNames.CAR4, FileNames.CAR5, FileNames.CAR6, FileNames.CAR7, FileNames.CAR8, FileNames.CAR9));
    }

    private void retrieveMainData(String data) {
        switch (data){
            case "1": {
                try {
                    addNewCar();
                    break;
                } catch (MyException e) {
                    System.err.println(e.toString());
                    return;
                }
            }
            case "2":{
                customSort();
                break;
            }
            case "3":{
                carsWithPriceAndBodyType();
                break;
            }
            case "4":{
                carsWithEngineTypeAndAlphabeticSort();
                break;
            }
            case "5":{
                statistics();
                break;
            }
            case "6":{
                mileageCar();
                break;
            }
            case "7":{
                tyreTypeMap();
                break;
            }
            case "8":{
                carsContainsComponents();
                break;
            }
            case "9":{
                loadDataFromJson();
                break;
            }
            case "10":{
                showCars();
                break;
            }
            case "q":{
                System.out.println("Koniec programu.");
                DataReader.close();
                break;
            }
            default:
                System.out.println("Niepopoprawny kod menu. Spróbuj jeszcze raz.");
        }
    }

    private void showCars() {
        carsService.getCars().forEach(System.out::println);
    }

    private void loadDataFromJson() {
        CarsService carsServiceJson = getCarServiceInstance();
        carsService.getCars().clear();
        carsService.getCars().addAll(carsServiceJson.getCars());
        System.out.println("Samochody zostały poprawnie załadowane z pliku json.");
    }

    private void carsContainsComponents() {
        System.out.println("Podaj komponenty - q kończy wpisywanie");
        List<Component> components = new ArrayList<>();
        Parser<Component> componentParser = new ComponentParser();
        String dataComponent = dataReader.getString();
        while (!dataComponent.equalsIgnoreCase("q")){
            components.add(componentParser.parse(dataComponent));
            dataComponent = dataReader.getString();
        }
        List<Car> carsContainsComponents = carsService.carsContainsComponents(components);
        System.out.println("carsContainsComponents");
        carsContainsComponents.forEach(System.out::println);
    }

    private void tyreTypeMap() {
        Map<TyreType, List<Car>> tyreTypeMap = carsService.tyreTypeMap();
        System.out.println("tyreTypeMap");
        tyreTypeMap.forEach((k, v) -> System.out.println(k + " " + v));
    }

    private void mileageCar() {
        Map<String, Integer> mileageCar = carsService
                .mileageCar()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> String.join(" ", "Car:", "model -", e.getKey().getModel(), ", price -", e.getKey().getPrice().toString()),
                        Map.Entry::getValue
                        )
                );
        System.out.println("mileageCar");
        mileageCar.forEach((k, v) -> System.out.println("key:" + k + " value: " + v));
    }

    private void statistics() {
        System.out.println("Podaj wielkosc dla ktrej chcesz statystykę");
        RetrieveDataService.retrieveMenu(createStatisticsMenu(), this::retrieveStatistics);
    }

    private void carsWithEngineTypeAndAlphabeticSort() {
        System.out.println("Podaj rodzaj silnika");
        EngineType engineType = dataReader.getEngineType();
        System.out.println("carsWithEngineTypeAndAlphabeticSort");
        List<Car> carsWithEngineTypeAndAlphSort = carsService.carsWithEngineTypeAndAlphabeticSort(engineType);
        carsWithEngineTypeAndAlphSort.forEach(System.out::println);
    }

    private void carsWithPriceAndBodyType() {
        System.out.println("Podaj rodzaj nadwozia");
        CarBodyType carBodyType = dataReader.getCarBodyType();
        System.out.println("Podaj cene min");
        BigDecimal minPrice = dataReader.getBigDecimal();
        System.out.println("Podaj cene max");
        BigDecimal maxPrice = dataReader.getBigDecimal();
        List<Car> carsWithPriceAndBodyType =  carsService.carsWithPriceAndBodyType(carBodyType, minPrice, maxPrice);
        System.out.println("carsWithPriceAndBodyType");
        carsWithPriceAndBodyType.forEach(System.out::println);
    }

    private void customSort() {
        String dataOrder = RetrieveDataService.retrieveMenuWithReturnValue(createSortOrderMenu(), this::retrieveOrderData);
        if (dataOrder != null && !dataOrder.equalsIgnoreCase("q")){
            RetrieveDataService.retrieveMenu(createSortMenu(), x -> retrieveSortData(x, dataOrder));
        }
    }

    private void addNewCar() {
        Car carToAdd = new Car();
        System.out.println("Podaj model");
        carToAdd.setModel(dataReader.getString());
        System.out.println("Podaj cene");
        carToAdd.setPrice(dataReader.getBigDecimal());
        System.out.println("Podaj liczbę przejechanych kilometrów");
        carToAdd.setMileage(dataReader.getInteger());
        System.out.println("Podaj typ silnik");
        Engine engineToAdd = new Engine();
        engineToAdd.seteType(dataReader.getEngineType());
        System.out.println("Podaj moc");
        engineToAdd.setPower(dataReader.getBigDecimal().doubleValue());
        System.out.println("Podaj kolor");
        CarBody carBodyToAdd = new CarBody();
        carBodyToAdd.setColour(dataReader.getColour());
        System.out.println("Podaj typ nadwozia");
        carBodyToAdd.setbType(dataReader.getCarBodyType());
        System.out.println("Podaj komponenty - q kończy dodawanie");
        List<Component> components = new ArrayList<>();
        Parser<Component> componentParser = new ComponentParser();
        String dataComponent = dataReader.getString();
        while (!dataComponent.equalsIgnoreCase("q")) {
            components.add(componentParser.parse(dataComponent));
            dataComponent = dataReader.getString();
        }
        carBodyToAdd.setComponents(components);
        Wheel wheelToAdd = new Wheel();
        System.out.println("Podaj model koła");
        wheelToAdd.setModel(dataReader.getString());
        System.out.println("Podaj wielkość koła");
        wheelToAdd.setSize(dataReader.getInteger());
        System.out.println("Podaj typ koła");
        wheelToAdd.setwType(dataReader.getTyreType());

        carToAdd.setEngine(engineToAdd);
        carToAdd.setCarBody(carBodyToAdd);
        carToAdd.setWheel(wheelToAdd);
        carsService.getCars().add(carToAdd);
    }

    private void retrieveOrderData(String data) {
        switch (data){
            case "r":{
                System.out.println("r");
                break;
            }
            case "m":{
                System.out.println("m");
                break;
            }
            case "q":{
                System.out.println("wyjdz.");
                break;
            }
            default:
                System.out.println("Niepopoprawny kod menu. Spróbuj jeszcze raz.");
        }
    }

    private void retrieveStatistics(String data) {
        switch (data){
            case "c":{
                System.out.println("cena");
                carsService.statistics(ParamType.PRICE);
                break;
            }
            case "p":{
                System.out.println("przebieg");
                carsService.statistics(ParamType.MILEAGE);
                break;
            }
            case "m":{
                System.out.println("moc");
                carsService.statistics(ParamType.POWER);
                break;
            }
            case "q":{
                System.out.println("wyjdz.");
                break;
            }
            default:
                System.out.println("Niepopoprawny kod menu. Spróbuj jeszcze raz.");
        }
    }

    private void retrieveSortData(String data, String orderData) {
        Order order = orderData.equals("r") ? Order.NATURAL : Order.REVERSE;
        switch (data){
            case "x":{
                List<Car> carList = carsService.customSort(SortBy.COMPONENTS_SIZE, order);
                System.out.println("SortBy.COMPONENTS_SIZE");
                carList.forEach(System.out::println);
                break;
            }
            case "y":{
                List<Car> carList = carsService.customSort(SortBy.POWER, order);
                System.out.println("SortBy.POWER");
                carList.forEach(System.out::println);
                break;
            }
            case "z":{
                List<Car> carList = carsService.customSort(SortBy.WHEEL_SIZE, order);
                System.out.println("SortBy.WHEEL_SIZE");
                carList.forEach(System.out::println);
                break;
            }
            case "q":{
                System.out.println("wyjdz");
                break;
            }
            default:
                System.out.println("Niepopoprawny kod menu. Spróbuj jeszcze raz.");
        }
    }

    private List<MenuItem> createMenu() {
        return Arrays.asList(
                MenuItem.builder().code("1").name("dodaj samochód").build(),
                MenuItem.builder().code("2").name("sortowanie według kryteriów do wyboru").build(),
                MenuItem.builder().code("3").name("samochodów o określonym rodzaju nadwozia i cenie z przediału <a, b>").build(),
                MenuItem.builder().code("4").name("sortowanie alfabetyczne według modeli samochodów oraz przekazanym typie silnika").build(),
                MenuItem.builder().code("5").name("statystyka").build(),
                MenuItem.builder().code("6").name("mapa samochodów i liczby kilometrów które przejechał").build(),
                MenuItem.builder().code("7").name("mapa rodzaju opony i liście samochodów które mają tą oponę").build(),
                MenuItem.builder().code("8").name("samochody które posiadają podaną kolekcję komponentów").build(),
                MenuItem.builder().code("9").name("zaladuj samochody z pliku json").build(),
                MenuItem.builder().code("10").name("pokaz wszystkie samochody").build(),
                MenuItem.builder().code("q").name("koniec aplikacji").build()
        );
    }

    private List<MenuItem> createSortMenu() {
        return Arrays.asList(
                MenuItem.builder().code("x").name("ilości komponentów").build(),
                MenuItem.builder().code("y").name("mocy silnika").build(),
                MenuItem.builder().code("z").name("rozmiaru opony").build(),
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

    private List<MenuItem> createStatisticsMenu() {
        return Arrays.asList(
                MenuItem.builder().code("c").name("cena").build(),
                MenuItem.builder().code("p").name("przebieg").build(),
                MenuItem.builder().code("m").name("moc").build(),
                MenuItem.builder().code("q").name("wyjdz").build()
        );
    }
}