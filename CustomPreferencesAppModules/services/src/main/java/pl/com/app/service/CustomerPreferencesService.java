package pl.com.app.service;


import pl.com.app.exceptions.MyException;
import pl.com.app.model.Customer;
import pl.com.app.model.Product;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class CustomerPreferencesService {
    private final Map<Customer, List<Product>> customerPreferencesServiceMap;
    private final DataLoaderService dataLoaderService = new DataLoaderService();

    CustomerPreferencesService(final String customersfileName, final String preferencesfileName, final String productsfileName) {
        customerPreferencesServiceMap = initData(customersfileName, preferencesfileName, productsfileName);
    }

    public Map<Customer, List<Product>> getCustomerPreferencesServiceMap() {
        return customerPreferencesServiceMap;
    }

    private Map<Customer, List<Product>> initData(final String customersfileName, final String preferencesfileName, final String productsfileName) {
        List<Customer> customers = dataLoaderService.loadClients(customersfileName);
        Map<Integer, String> preferences = dataLoaderService.loadPreferences(preferencesfileName);
        List<Product> products = dataLoaderService.loadProducts(productsfileName);

        return customers
                .stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        c -> {
                            List<Product> productList = new ArrayList<>();
                            Stream.of(c.getPreferences().split(""))
                                    .forEach(p -> {
                                            final String category = preferences.get(Integer.valueOf(p));
                                            products
                                                    .stream()
                                                    .filter(x -> x.getCategory().equals(category))
                                                    .sorted(Comparator.comparing(Product::getPrice))
                                                    .forEach(x -> {
                                                        if (c.getCash() >= x.getPrice()) {
                                                            c.setCash(c.getCash() - x.getPrice());
                                                            productList.add(x);
                                                        }
                                                    });

                                    });
                            return productList;
                        },
                        (v1, v2) -> {v1.addAll(v2); return v1;}
                ));
    }

    List<Customer> customersBoughtTheMostProducts() {
        Map<Customer, Integer> productsAmount = customerPreferencesServiceMap
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().size()
                ));

        Integer max = productsAmount.values().stream().max(Comparator.naturalOrder()).orElseThrow(() -> new MyException("MAX IS NULL"));

        return productsAmount
                .entrySet()
                .stream()
                .filter(e -> e.getValue() == max)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    List<Map.Entry<Customer, Long>> customersPaidMaxPrice() {
        return customerPreferencesServiceMap
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue()
                                .stream()
                                .map(Product::getPrice)
                                .collect(Collectors.summarizingInt(x -> x))
                                .getSum()
                ))
                .entrySet()
                .stream()
                .collect(Collectors.groupingBy(Map.Entry::getValue))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .orElseThrow(() -> new MyException("MAX PRICE IS NULL"));
    }

    Map<Product, Long> frequencyProducts() {
        return customerPreferencesServiceMap
                .values()
                .stream()
                .flatMap(List<Product>::stream)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    List<Product> theLeastFrequencyProducts(final String productFileName) {
        if (productFileName == null) {
            throw new MyException("FILENAME IS NULL");
        }

        Set<Product> productBoughtMinOnce = frequencyProducts().keySet();
        List<Product> productDontBought = dataLoaderService.loadProducts(productFileName)
                .stream()
                .filter(x -> !productBoughtMinOnce.contains(x))  // produkty ktore nie zostaly kupione ani razu
                .collect(Collectors.toList());

        if (!productDontBought.isEmpty()) {
            return productDontBought;
        } else {
            final long minFrequency = frequencyProducts()
                    .values()
                    .stream()
                    .min(Comparator.naturalOrder())
                    .orElseThrow(() -> new MyException("MIN FREQUENCY PRODUCT IS NULL"));

            return frequencyProducts()
                    .entrySet()
                    .stream()
                    .filter(x -> x.getValue() == minFrequency)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }
    }

    List<Product> theMostFrequencyProducts() {
        final long maxFrequency = frequencyProducts()
                .values()
                .stream().max(Comparator.naturalOrder())
                .orElseThrow(() -> new MyException("MAX FREQUENCY PRODUCT IS NULL"));

        return frequencyProducts()
                .entrySet()
                .stream()
                .filter(x -> x.getValue() == maxFrequency)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    Map<String, Long> frequencyCategory() {
        return customerPreferencesServiceMap
                .values()
                .stream()
                .flatMap(List<Product>::stream)
                .map(Product::getCategory)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1 + v2,
                        LinkedHashMap::new
                ));
    }
}