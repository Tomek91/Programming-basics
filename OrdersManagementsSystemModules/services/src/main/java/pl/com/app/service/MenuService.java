package pl.com.app.service;

import pl.com.app.models.Customer;
import pl.com.app.models.Product;
import pl.com.app.models.enums.Category;
import pl.com.app.parsers.json.FileNames;
import pl.com.app.reader.DataReader;
import pl.com.app.service.utils.MenuItem;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MenuService {
    static final String PREFIX = "<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>";
    private OrdersService ordersService;

    public void ordersApp() {
        System.out.println("Witaj w symulatorze zamówień !!!");
        System.out.println("Oto Twoje menu. Każda liczba odpowiada akcji, którą chcesz wykonać.\n" +
                "Pamiętaj, w każdej chwili możesz zakończyć aplikację naciskając 'q'.");

        ordersService = new OrdersService(FileNames.ORDERS);
        RetrieveDataService.retrieveMenu(createMenu(), this::retrieveMainData);
    }

    private void retrieveMainData(String data) {
        switch (data) {
            case "1": {
                addNewOrder();
                break;
            }
            case "2": {
                averagePriceBetweenDates();
                break;
            }
            case "3": {
                maxProductInCategory();
                break;
            }
            case "4": {
                customerProducts();
                break;
            }
            case "5": {
                maxAndMinOrdersDates();
                break;
            }
            case "6": {
                maxPriceOrdersCustomer();
                break;
            }
            case "7": {
                sumPriceOrders();
                break;
            }
            case "8": {
                numberOfCustomersBoughtProductAmount();
                break;
            }
            case "9": {
                mostFrequentlyCategory();
                break;
            }
            case "10": {
                numberOfProductsByMonths();
                break;
            }
            case "11": {
                numberOfCategoryByMonths();
                break;
            }
            case "12": {
                loadDataFromJson();
                break;
            }
            case "13": {
                showAllOrders();
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

    private void showAllOrders() {
        ordersService.getOrders().forEach(System.out::println);
    }

    private void loadDataFromJson() {
        OrdersService ordersServiceJson = new OrdersService(FileNames.ORDERS);
        ordersService.getOrders().clear();
        ordersService.getOrders().addAll(ordersServiceJson.getOrders());
        System.out.println("Zamówienia zostały poprawnie załadowane z pliku json.");
    }

    private void numberOfCategoryByMonths() {
        Map<Month, Category> numberOfCategoryByMonths = ordersService.numberOfCategoryByMonths();
        System.out.println("numberOfCategoryByMonths");
        numberOfCategoryByMonths.forEach((k, v) -> System.out.println(k + " " + v));
    }

    private void numberOfProductsByMonths() {
        Map<Month, Integer> numberOfProductsByMonths = ordersService.numberOfProductsByMonths();
        System.out.println("numberOfProductsByMonths");
        numberOfProductsByMonths.forEach((k, v) -> System.out.println(k + " " + v));
    }

    private void mostFrequentlyCategory() {
        Category mostFrequentlyCategory = ordersService.mostFrequentlyCategory();
        System.out.println("mostFrequentlyCategory " + mostFrequentlyCategory);
    }

    private void numberOfCustomersBoughtProductAmount() {
        final int N = 9;
        Integer numberOfCustomersBoughtProductAmount = ordersService.numberOfCustomersBoughtProductAmount(N);
        System.out.println("numberOfCustomersBoughtProductAmount N = " + N + " - " + numberOfCustomersBoughtProductAmount);
    }

    private void sumPriceOrders() {
        BigDecimal sumPriceOrders = ordersService.sumPriceOrders();
        System.out.println("sumPriceOrders " + sumPriceOrders);
    }

    private void maxPriceOrdersCustomer() {
        Customer maxPriceOrdersCustomer = ordersService.maxPriceOrdersCustomer();
        System.out.println("maxPriceOrdersCustomer " + maxPriceOrdersCustomer);
    }

    private void maxAndMinOrdersDates() {
        List<LocalDate> maxOrdersDate = ordersService.maxOrdersDate();
        System.out.println("maxOrdersDate " + maxOrdersDate);
        List<LocalDate> minOrdersDate = ordersService.minOrdersDate();
        System.out.println("minOrdersDate " + minOrdersDate);
    }

    private void customerProducts() {
        Map<Customer, List<Product>> customerProducts = ordersService.customerProducts();
        System.out.println("customerProducts");
        customerProducts.forEach((k, v) -> System.out.println(k + " " + v));
        //OrdersService.sendProductsToCustomers(customerProducts);
    }

    private void maxProductInCategory() {
        Map<Category, Product> maxProduktInCategory = ordersService.maxProductInCategory();
        System.out.println("maxProductInCategory");
        maxProduktInCategory.forEach((k, v) -> System.out.println(k + " " + v));
    }

    private void averagePriceBetweenDates() {
        BigDecimal averagePriceBetweenDates = ordersService.averagePriceBetweenDates(LocalDate.of(2019, 1, 1), LocalDate.of(2019, 11, 11));
        System.out.println("averagePriceBetweenDates " + averagePriceBetweenDates);
    }

    private void addNewOrder() {
        ordersService.addNewOrder();
    }

    private List<MenuItem> createMenu() {
        return Arrays.asList(
                MenuItem.builder().code("1").name("dodaj nowe zamówienie").build(),
                MenuItem.builder().code("2").name("obliczenie średniej ceny wszystkich produktów, które zamówiono w przedziale czasowym <d1, d2>").build(),
                MenuItem.builder().code("3").name("wyznaczenie dla każdej kategorii produktu o największej cenie").build(),
                MenuItem.builder().code("4").name("przygotowanie dla każdego klienta zestawienia wszystkich produktów, które zamówił i wysłanie na jego adres e-mail tak otrzymanego zestawienia").build(),
                MenuItem.builder().code("5").name("wyznaczenie daty, dla której złożono najwięcej zamówień oraz daty dla której złożono najmniej zamówień").build(),
                MenuItem.builder().code("6").name("wyznacz informację o kliencie, który zapłacił najwięcej za złożone zamówienia").build(),
                MenuItem.builder().code("7").name("zakładamy, że wszystkie zamówienia, których data realizacji jest nie później niż 2 dni od daty aktualnej dostają 2% rabat. Zamówienie dla klientów przed 25 rokiem życia dostają rabat 3%. Uwaga rabaty nie sumuję się, tylko wybierany jest korzystniejszy wariant. Uwzględniając te informacje wyznacz całkowitą cenę wszystkich zamówień").build(),
                MenuItem.builder().code("8").name("wyznacz liczbę klientów, którzy za każdym razem zamówili co najmniej x sztuk zamawianego produktu. Wartość zmiennej x przekazywana jest przykładowo jako argument metody. Informacje o takich klientach zapisz do pliku JSON").build(),
                MenuItem.builder().code("9").name("wyznacz kategorię, której produkty kupowano najczęściej").build(),
                MenuItem.builder().code("10").name("wykonaj zestawienie, w którym podasz nazwę miesiąca oraz ilość produktów zamówionych w tym miesiącu. Zestawienie posortuj malejąco według ilości zamówionych produktów").build(),
                MenuItem.builder().code("11").name("wykonaj zestawienie, w którym podasz nazwę miesiąca oraz kategorię produktu, której produkty najchętniej w tym miesiącu zamawiano").build(),
                MenuItem.builder().code("12").name("zaladuj zamówienia z pliku json").build(),
                MenuItem.builder().code("13").name("pokaż listę zamówień").build(),
                MenuItem.builder().code("q").name("koniec aplikacji").build()
        );
    }
}
