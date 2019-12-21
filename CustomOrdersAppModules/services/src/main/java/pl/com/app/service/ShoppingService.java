package pl.com.app.service;


import org.eclipse.collections.impl.collector.Collectors2;
import pl.com.app.exceptions.MyException;
import pl.com.app.models.Customer;
import pl.com.app.models.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ShoppingService {
    private final Map<Customer, Map<Product, Long>> map;
    private final DataLoaderService dataLoaderService = new DataLoaderService();

    ShoppingService(String... filesName) {
        this.map = retrieveFiles(filesName);
    }

    public Map<Customer, Map<Product, Long>> getMap() {
        return map;
    }

    private Map<Customer, Map<Product, Long>> retrieveFiles(String... filesName) {

        return Arrays.stream(filesName)
                .flatMap(fileName -> dataLoaderService.loadCustomerOrders(fileName).stream())
                .collect(Collectors.toMap(
                        x -> Customer
                                .builder()
                                .name(x.getName())
                                .surname(x.getSurname())
                                .age(x.getAge())
                                .cash(x.getCash())
                                .build(),
                        x -> x.getProducts()
                                .stream()
                                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())),
                        (m1, m2) -> Stream.of(m1, m2)
                                .flatMap(m -> m.entrySet().stream())
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        Map.Entry::getValue,
                                        (v1, v2) -> v1 + v2))
                ));
    }

    Customer customerMaxPayment() {
        if (this.getMap() == null) {
            throw new MyException("MAP IS NULL");
        }
        return this.getMap()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue()
                                .entrySet()
                                .stream()
                                .map(x -> x.getKey().getPrice().multiply(new BigDecimal(x.getValue())))
                                .collect(Collectors2.summarizingBigDecimal(x -> x))
                                .getSum(),
                        BigDecimal::add
                ))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new MyException("RESULT IS NULL"));
    }

    Customer customerMaxPaymentByCategory(final String cat) {
        if (this.getMap() == null || cat == null) {
            throw new MyException("ITEMS ARE NULL");
        }
        return this.getMap()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue()
                                .entrySet()
                                .stream()
                                .filter(x -> x.getKey().getCategory().equals(cat))
                                .map(x -> x.getKey().getPrice().multiply(new BigDecimal(x.getValue())))
                                .collect(Collectors2.summarizingBigDecimal(x -> x))
                                .getSum(),
                        BigDecimal::add,
                        LinkedHashMap::new
                ))
                .entrySet()
                .stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new MyException("RESULT IS NULL"));
    }

    Map<Integer, Set<String>> ageCustomersAndMostFrequentlyCategory() {
        if (this.getMap() == null) {
            throw new MyException("ITEMS ARE NULL");
        }

        Map<Integer, Map<String, Long>> frequenciesCatByAge = getMap()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().getAge(),
                        e -> e.getValue()
                                .entrySet()
                                .stream()
                                .collect(Collectors.toMap(
                                        x -> x.getKey().getCategory(),
                                        Map.Entry::getValue,
                                        (v1, v2) -> v1 + v2,
                                        LinkedHashMap::new
                                )),
                        (e1, e2) -> Stream.of(e1, e2)
                                .flatMap(m -> m.entrySet().stream())
                                .collect(Collectors.toMap(
                                            Map.Entry::getKey,
                                            Map.Entry::getValue,
                                            (v1, v2) -> v1 + v2
                                        )
                                )
                ));

        Map<Integer, Set<String>> ageClientsFrequencyCat = new HashMap<>();
        frequenciesCatByAge.forEach((k, v) -> {
            final Long max = v.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getValue).orElseThrow(() -> new NullPointerException("MAX IS NULL"));

            ageClientsFrequencyCat.put(k, v.entrySet()
                    .stream()
                    .filter(e -> e.getValue() == max)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toSet()));
        });

        return ageClientsFrequencyCat;
    }

    Map<String, BigDecimal> avgPriceByCategory() {

        if (this.getMap() == null) {
            throw new MyException("ITEMS ARE NULL");
        }

        Map<String, Map<BigDecimal, Long>> avgPrice = new HashMap<>();

        this.getMap().forEach((k, v) -> {
            v.forEach((kk, vv) -> {
                Map<BigDecimal, Long> insideMap;
                if (avgPrice.containsKey(kk.getCategory())) {
                    insideMap = avgPrice.get(kk.getCategory());
                    if (insideMap.containsKey(kk.getPrice())) {
                        insideMap.put(kk.getPrice(), vv + insideMap.get(kk.getPrice()));
                    } else {
                        insideMap.put(kk.getPrice(), vv);
                    }
                } else {
                    insideMap = new HashMap<>();
                    insideMap.put(kk.getPrice(), vv);
                }
                avgPrice.put(kk.getCategory(), insideMap);
            });
        });

        Map<String, BigDecimal> avgPriceByCategory = new HashMap<>();
        avgPrice.forEach((k, v) -> {
            Long size = v.values().stream().mapToLong(x -> x).sum();
            BigDecimal sum = v.entrySet()
                    .stream()
                    .map(x -> x.getKey().multiply(new BigDecimal(x.getValue())))
                    .collect(Collectors2.summingBigDecimal(x -> x));

            avgPriceByCategory.put(k, sum.divide(new BigDecimal(size), 2, RoundingMode.CEILING));
        });

        return avgPriceByCategory;
    }

    TreeSet<Product> maxMinPriceByCategory(final String category) {
        if (this.getMap() == null || category == null) {
            throw new MyException("ITEMS ARE NULL");
        }

        return this.getMap()
                .values()
                .stream()
                .flatMap(e -> e.keySet().stream())
                .filter(x -> x.getCategory().equals(category))
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Product::getPrice).thenComparing(Product::getName))));
    }

    Map<String, List<Customer>> theMostFrequentlyCustomersInCategory() {
        if (this.getMap() == null) {
            throw new MyException("ITEMS ARE NULL");
        }

        Map<String, Map<Customer, Long>> customersOrdersByCategory = new HashMap<>();

        this.getMap().forEach((k, v) -> {
            v.forEach((kk, vv) -> {
                Map<Customer, Long> insideMap;
                if (customersOrdersByCategory.containsKey(kk.getCategory())) {
                    insideMap = customersOrdersByCategory.get(kk.getCategory());
                    if (insideMap.containsKey(k)) {
                        insideMap.put(k, vv + insideMap.get(k));
                    } else {
                        insideMap.put(k, vv);
                    }
                } else {
                    insideMap = new HashMap<>();
                    insideMap.put(k, vv);
                }
                customersOrdersByCategory.put(kk.getCategory(), insideMap);
            });
        });

        Map<String, List<Customer>> theMostFrequentlyCustomersInCategory = new HashMap<>();

        customersOrdersByCategory.forEach((k, v) -> {
            final Long max = v.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getValue).orElseThrow(() -> new NullPointerException("MAX IS NULL"));

            theMostFrequentlyCustomersInCategory.put(k, v.entrySet()
                                                            .stream()
                                                            .filter(e -> e.getValue() == max)
                                                            .map(Map.Entry::getKey)
                                                            .collect(Collectors.toList())
            );
        });

        return theMostFrequentlyCustomersInCategory;
    }

    Map<Customer, BigDecimal> customersDebt() {
        if (this.getMap() == null) {
            throw new MyException("ITEMS ARE NULL");
        }

        return this.getMap()
                .keySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> e,
                        e -> e.getCash().subtract(this.getMap()
                                .get(e)
                                .keySet()
                                .stream()
                                .map(x -> x.getPrice().multiply(new BigDecimal(this.getMap().get(e).get(x))))
                                .collect(Collectors2.summarizingBigDecimal(x -> x))
                                .getSum())
                ));
    }
}
