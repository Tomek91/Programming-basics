package pl.com.app.service;

import pl.com.app.json.FileNames;
import pl.com.app.model.Customer;
import pl.com.app.model.Product;
import pl.com.app.reader.DataReader;
import pl.com.app.service.utils.MenuItem;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MenuService {
    public static final String PREFIX = "<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>";
    private CustomerPreferencesService customerPreferencesService;

    public void customersPreferencesApp() {
        System.out.println("Witaj w symulatorze preferencji klientów !!!");
        System.out.println("Oto Twoje menu. Każda liczba odpowiada akcji, którą chcesz wykonać.\n" +
                "Pamiętaj, w każdej chwili możesz zakończyć aplikację naciskając 'q'.");

        customerPreferencesService = new CustomerPreferencesService(FileNames.CUSTOMERS, FileNames.PREFERENCES, FileNames.PRODUCTS);
        RetrieveDataService.retrieveMenu(createMenu(), this::retrieveMainData);
    }

    private void retrieveMainData(String data) {
        switch (data) {
            case "1": {
                customersBoughtTheMostProducts();
                break;
            }
            case "2": {
                customersPaidMaxPrice();
                break;
            }
            case "3": {
                frequencyProducts();
                break;
            }
            case "4": {
                theMostFrequencyProducts();
                break;
            }
            case "5": {
                theLeastFrequencyProducts();
                break;
            }
            case "6": {
                frequencyCategory();
                break;
            }
            case "7": {
                getCustomerPreferencesServiceMap();
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

    private void getCustomerPreferencesServiceMap() {
        customerPreferencesService
                .getCustomerPreferencesServiceMap()
                .forEach((k, v) -> System.out.println(k + " " + v));
    }

    private void frequencyCategory() {
        Map<String, Long> frequencyCategory = customerPreferencesService.frequencyCategory();
        System.out.println("frequencyCategory ");
        frequencyCategory.forEach((k, v) -> System.out.println(k + " " + v));
    }

    private void theLeastFrequencyProducts() {
        List<Product> frequencyMinProducts = customerPreferencesService.theLeastFrequencyProducts(FileNames.PRODUCTS);
        System.out.println("theLeastFrequencyProducts ");
        frequencyMinProducts.forEach(System.out::println);
    }

    private void theMostFrequencyProducts() {
        List<Product> theMostFrequencyProducts = customerPreferencesService.theMostFrequencyProducts();
        System.out.println("theMostFrequencyProducts ");
        theMostFrequencyProducts.forEach(System.out::println);
    }

    private void frequencyProducts() {
        Map<Product, Long> frequencyProducts = customerPreferencesService.frequencyProducts();
        System.out.println("frequencyProducts ");
        frequencyProducts.forEach((k, v) -> System.out.println(k + " " + v));
    }

    private void customersPaidMaxPrice() {
        List<Map.Entry<Customer, Long>> maxPriceProducts = customerPreferencesService.customersPaidMaxPrice();
        System.out.println("customersPaidMaxPrice ");
        maxPriceProducts.forEach(k -> System.out.println(k.getKey() + " " + k.getValue()));
    }

    private void customersBoughtTheMostProducts() {
        List<Customer> maxProducts = customerPreferencesService.customersBoughtTheMostProducts();
        System.out.println("customersBoughtTheMostProducts ");
        maxProducts.forEach(System.out::println);
    }

    private List<MenuItem> createMenu() {
        return Arrays.asList(
                MenuItem.builder().code("1").name("klient, który zakupił najwięcej produktów").build(),
                MenuItem.builder().code("2").name("klient, który zakupił produkty o łącznie najwyższej wartości").build(),
                MenuItem.builder().code("3").name("zestawienie produktów oraz ile razy był wybierany przez wszystkich klientów").build(),
                MenuItem.builder().code("4").name("produkt najczęściej wybierany").build(),
                MenuItem.builder().code("5").name("produkt najrzadziej wybierany").build(),
                MenuItem.builder().code("6").name("zestawienie kategorii posortowane malejąco według popularności ich wybierania").build(),
                MenuItem.builder().code("7").name("pokaż mapę klientów z listami produktów").build(),
                MenuItem.builder().code("q").name("koniec aplikacji").build()
        );
    }
}
