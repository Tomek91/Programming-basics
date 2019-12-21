package pl.com.app.service;

import pl.com.app.json.FileNames;
import pl.com.app.models.Customer;
import pl.com.app.models.Product;
import pl.com.app.reader.DataReader;
import pl.com.app.service.utils.MenuItem;

import java.math.BigDecimal;
import java.util.*;


public class MenuService {
    static final String PREFIX = "<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>";
    private static final List<String> category = Arrays.asList("Chrupki", "Elektronika", "Biurowe", "Ksiazka", "Sprzet", "Owoc", "Warzywo", "Buty", "Dom", "Meble");
    private ShoppingService shoppingService;

    public void customersOrdersApp() {
        System.out.println("Witaj w symulatorze zamówień klientów !!!");
        System.out.println("Oto Twoje menu. Każda liczba odpowiada akcji, którą chcesz wykonać.\n" +
                "Pamiętaj, w każdej chwili możesz zakończyć aplikację naciskając 'q'.");

        shoppingService = new ShoppingService(FileNames.CUSTOMERS_PRODUCTS_1, FileNames.CUSTOMERS_PRODUCTS_2, FileNames.CUSTOMERS_PRODUCTS_3);
        RetrieveDataService.retrieveMenu(createMenu(), this::retrieveMainData);
    }

    private void retrieveMainData(String data) {
        switch (data) {
            case "1": {
                customerMaxPayment();
                break;
            }
            case "2": {
                customerMaxPaymentByCategory();
                break;
            }
            case "3": {
                ageCustomersAndMostFrequentlyCategory();
                break;
            }
            case "4": {
                avgPriceByCategory();
                break;
            }
            case "5": {
                maxMinPriceByCategory();
                break;
            }
            case "6": {
                theMostFrequentlyCustomersInCategory();
                break;
            }
            case "7": {
                customersDebt();
                break;
            }
            case "8": {
                showMap();
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

    private void showMap() {
        shoppingService.getMap().forEach((k, v) -> System.out.println("k " + k + " v " + v));
    }

    private void customersDebt() {
        Map<Customer, BigDecimal> customersDebt = shoppingService.customersDebt();
        customersDebt.forEach((k, v) -> System.out.println(k + " " + v));
        System.out.println("------------------------------------------------------");
    }

    private void theMostFrequentlyCustomersInCategory() {
        Map<String, List<Customer>> theMostFrequentlyCustomersInCategory = shoppingService.theMostFrequentlyCustomersInCategory();
        theMostFrequentlyCustomersInCategory.forEach((k, v) -> System.out.println(k + " " + v));
    }

    private void maxMinPriceByCategory() {
        for (String s : category) {
            TreeSet<Product> maxMinPriceByCategory = shoppingService.maxMinPriceByCategory(s);
            System.out.println("Category " + s + "\n min: " + maxMinPriceByCategory.first() + "\n max: " + maxMinPriceByCategory.last());
        }
    }

    private void avgPriceByCategory() {
        Map<String, BigDecimal> avgPriceByCategory = shoppingService.avgPriceByCategory();
        avgPriceByCategory.forEach((k, v) -> System.out.println(k + " " + v));
    }

    private void ageCustomersAndMostFrequentlyCategory() {
        Map<Integer, Set<String>> ageCustomersAndMostFrequentlyCategory = shoppingService.ageCustomersAndMostFrequentlyCategory();
        ageCustomersAndMostFrequentlyCategory.forEach((k, v) -> System.out.println(k + " " + v));
    }

    private void customerMaxPaymentByCategory() {
        for (String s : category) {
            System.out.println(s + " " + shoppingService.customerMaxPaymentByCategory(s));
        }
    }

    private void customerMaxPayment() {
        Customer customerMaxPayment = shoppingService.customerMaxPayment();
        System.out.println(customerMaxPayment.toString());
    }

    private List<MenuItem> createMenu() {
        return Arrays.asList(
                MenuItem.builder().code("1").name("klient który zapłacił najwięcej za wszystkie zakupy").build(),
                MenuItem.builder().code("2").name("klient który zapłacił najwięcej za zakupy z wybranej kategorii").build(),
                MenuItem.builder().code("3").name("zestawienie wieku klientów oraz kategorie produktów, które najchętniej w tym wieku kupowano").build(),
                MenuItem.builder().code("4").name("zestawienie średniej ceny produktów w danej kategorii").build(),
                MenuItem.builder().code("5").name("zestawienie kategorii produktów - najdroższy oraz produkt najtańszy").build(),
                MenuItem.builder().code("6").name("klienci którzy kupowali najczęściej produkty danej kategorii").build(),
                MenuItem.builder().code("7").name("zestawienie klientów i różnicy pomiędzy kwotą do zapłaty oraz gotówką").build(),
                MenuItem.builder().code("8").name("pokaz zestawienie mapy klientów i ich zakupów").build(),
                MenuItem.builder().code("q").name("koniec aplikacji").build()
        );
    }
}
