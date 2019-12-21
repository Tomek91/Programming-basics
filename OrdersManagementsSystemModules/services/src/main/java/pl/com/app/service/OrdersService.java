package pl.com.app.service;


import org.eclipse.collections.impl.collector.Collectors2;
import pl.com.app.email.EmailUtil;
import pl.com.app.exceptions.MyException;
import pl.com.app.models.Customer;
import pl.com.app.models.Order;
import pl.com.app.models.Product;
import pl.com.app.models.enums.Category;
import pl.com.app.parsers.json.CustomerConverter;
import pl.com.app.parsers.json.FileNames;
import pl.com.app.reader.DataReader;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;


public class OrdersService {
    private final List<Order> orders = new ArrayList<>();
    private DataReader dataReader = new DataReader();
    private DataLoaderService dataLoaderService = new DataLoaderService();

    public OrdersService(String fileName) {
        orders.addAll(dataLoaderService.loadOrdersModel(fileName));
    }

    public List<Order> getOrders() {
        return orders;
    }

    public DataReader getDataReader() {
        return dataReader;
    }

    public void setDataReader(DataReader dataReader) {
        this.dataReader = dataReader;
    }

    public DataLoaderService getDataLoaderService() {
        return dataLoaderService;
    }

    public void setDataLoaderService(DataLoaderService dataLoaderService) {
        this.dataLoaderService = dataLoaderService;
    }

    void addNewOrder() {
        try {
            String data;
            do {
                Customer customer = new Customer();
                System.out.println("podaj imie");
                customer.setName(dataReader.getString());
                System.out.println("podaj nazwisko");
                customer.setSurname(dataReader.getString());
                System.out.println("podaj wiek");
                customer.setAge(dataReader.getInteger());
                System.out.println("podaj email");
                customer.setEmail(dataReader.getString());

                Product product = new Product();
                System.out.println("podaj nazwe produktu");
                product.setName(dataReader.getString());
                System.out.println("podaj cene");
                product.setPrice(dataReader.getBigDecimal());
                System.out.println("podaj kategorie");
                product.setCategory(dataReader.getCategory());

                System.out.println("podaj ilosc produktow");
                Integer quantity = dataReader.getInteger();
                System.out.println("podaj date zamowienia");
                LocalDate orderDate = dataReader.getLocalDate();
                orders.add(new Order(customer, product, quantity, orderDate));

                System.out.println("podaj zamowienia recznie / q konczy wpisywanie");
                data = dataReader.getString();
            } while (!data.equalsIgnoreCase("q"));
        } catch (MyException e) {
            System.err.println(e.toString());
            return;
        }
    }

    Map<Category, Product> maxProductInCategory() {
        return this.getOrders()
                .stream()
                .collect(Collectors.groupingBy(x -> x.getProduct().getCategory()))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue()
                                .stream()
                                .map(Order::getProduct)
                                .max(Comparator.comparing(Product::getPrice))
                                .orElseThrow(() -> new MyException("MAX PRODUCT IN CATEGORY EXCEPTION"))
                ));
    }

    BigDecimal averagePriceBetweenDates(final LocalDate from, final LocalDate to) {
        if (from == null || to == null) {
            throw new MyException("ARGS ARE NULL");
        }
        return this.getOrders()
                .stream()
                .filter(x -> x.getOrderDate().isAfter(from) && x.getOrderDate().isBefore(to))
                .map(Order::getProduct)
                .map(Product::getPrice)
                .collect(Collectors2.summarizingBigDecimal(x -> x))
                .getAverage()
                .setScale(2, BigDecimal.ROUND_CEILING);
    }

    Map<Customer, List<Product>> customerProducts() {
        return this.getOrders()
                .stream()
                .collect(Collectors.groupingBy(Order::getCustomer))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream().map(Order::getProduct).collect(Collectors.toList())
                ));
    }

    private Map<LocalDate, List<Order>> groupingByDate() {
        return this.getOrders()
                .stream()
                .collect(Collectors.groupingBy(Order::getOrderDate));
    }

    List<LocalDate> maxOrdersDate() {
        Map<LocalDate, List<Order>> groupingByDate = groupingByDate();
        final Integer maxOrdersSize = groupingByDate
                .values()
                .stream()
                .map(List::size)
                .max(Comparator.naturalOrder())
                .orElseThrow(() -> new MyException("MAX ORDERS SIZE EXCEPTION"));

        return groupingByDate
                .entrySet()
                .stream()
                .filter(x -> x.getValue().size() == maxOrdersSize)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    List<LocalDate> minOrdersDate() {
        Map<LocalDate, List<Order>> groupingByDate = groupingByDate();
        final Integer minOrdersSize = groupingByDate
                .values()
                .stream()
                .map(List::size)
                .min(Comparator.naturalOrder())
                .orElseThrow(() -> new MyException("MIN ORDERS SIZE EXCEPTION"));

        return groupingByDate
                .entrySet()
                .stream()
                .filter(x -> x.getValue().size() == minOrdersSize)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    Customer maxPriceOrdersCustomer() {
        return this.getOrders()
                .stream()
                .collect(Collectors.groupingBy(Order::getCustomer))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue()
                                .stream()
                                .collect(Collectors2.summarizingBigDecimal(x -> x.getProduct().getPrice()))
                                .getSum()
                ))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new MyException("MAX PRICE ORDERS CUSTOMER EXCEPTION"));
    }

    Category mostFrequentlyCategory() {
        return this.getOrders()
                .stream()
                .collect(Collectors.groupingBy(x -> x.getProduct().getCategory()))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().size()
                ))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new MyException("MOST FREQUENTLY CATEGORY EXCEPTION"));
    }

    BigDecimal sumPriceOrders() {
        return this.getOrders()
                .stream()
                .map(x -> {
                    BigDecimal price = x.getProduct().getPrice().multiply(new BigDecimal(x.getQuantity()));
                    if (x.getCustomer().getAge() < 25) {
                        price = price.multiply(new BigDecimal(0.97));
                    } else if (x.getOrderDate().compareTo(LocalDate.now()) >= 0 && x.getOrderDate().compareTo(LocalDate.now().plusDays(2L)) <= 0) {
                        price = price.multiply(new BigDecimal(0.98));
                    }
                    return price.setScale(2, BigDecimal.ROUND_HALF_UP);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    Integer numberOfCustomersBoughtProductAmount(final Integer N) {
        if (N == null) {
            throw new MyException("N IS NULL");
        }
        List<Customer> customerList = this.getOrders()
                .stream()
                .filter(x -> x.getQuantity() >= N)
                .map(Order::getCustomer)
                .collect(Collectors.toList());

        CustomerConverter customerConverter = new CustomerConverter(FileNames.CUSTOMER_AMOUNT);
        customerConverter.toJson(customerList);
        return customerList.size();
    }

    Map<Month, Integer> numberOfProductsByMonths() {
        return this.getOrders()
                .stream()
                .collect(Collectors.groupingBy(x -> x.getOrderDate().getMonth()))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue()
                                .stream()
                                .map(Order::getQuantity)
                                .mapToInt(Integer::intValue)
                                .sum()
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));
    }

    Map<Month, Category> numberOfCategoryByMonths() {
        return this.getOrders()
                .stream()
                .collect(Collectors.groupingBy(x -> x.getOrderDate().getMonthValue()))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue()
                                .stream()
                                .collect(Collectors.groupingBy(x -> x.getProduct().getCategory()))
                                .entrySet()
                                .stream()
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        ee -> ee.getValue()
                                                .stream()
                                                .map(Order::getQuantity)
                                                .mapToInt(Integer::intValue)
                                                .sum()
                                ))
                                .entrySet()
                                .stream()
                                .max(Map.Entry.comparingByValue())
                                .map(Map.Entry::getKey)
                                .orElseThrow(() -> new MyException("NUMBER OF CATEGORY BY MONTHS EXCEPTION"))
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        e -> Month.of(e.getKey()),
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));
    }

    public static void sendProductsToCustomers(Map<Customer, List<Product>> customerProducts) {
        if (customerProducts == null) {
            throw new MyException("CUSTOMER PRODUCTS ARE NULL");
        }
        customerProducts.forEach((k, v) -> {
            EmailUtil.send(k.getEmail(), "Zamowienia", v.stream()
                    .map(Product::toString)
                    .collect(Collectors.joining("\n"))
            );
        });
    }
}
