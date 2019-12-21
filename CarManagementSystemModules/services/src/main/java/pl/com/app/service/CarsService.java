package pl.com.app.service;


import pl.com.app.exceptions.MyException;
import pl.com.app.model.Car;
import pl.com.app.model.enums.Color;
import pl.com.app.model.enums.Order;
import pl.com.app.model.enums.SortBy;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CarsService {
    private final List<Car> cars;
    private final DataLoaderService dataLoaderService = new DataLoaderService();

    CarsService(String fileName) {
        cars = dataLoaderService.loadCarsManagement(fileName);
    }

    public List<Car> getCars() {
        return cars;
    }

    @Override
    public String toString() {
        return cars.stream().map(Car::toString).collect(Collectors.joining());
    }

    List<Car> customSort(SortBy sortBy, Order order) {
        if (sortBy == null || order == null) {
            throw new MyException("ARGS ARE NULL");
        }
        List<Car> carList = null;
        if (sortBy == SortBy.MODEL) {
            carList = sortByModel(order);
        } else if (sortBy == SortBy.MILEAGE) {
            carList = sortByMileage(order);
        } else if (sortBy == SortBy.COLOUR) {
            carList = sortByColour(order);
        } else {
            carList = sortByPrice(order);
        }
        return carList;
    }

    private List<Car> sortByModel(Order order) {
        return cars
                .stream()
                .sorted(Comparator.comparing(Car::getModel, (order == Order.NATURAL ? Comparator.naturalOrder() : Comparator.reverseOrder())))
                .collect(Collectors.toList());
    }

    private List<Car> sortByMileage(Order order) {
        return cars
                .stream()
                .sorted(Comparator.comparing(Car::getMileage, (order == Order.NATURAL ? Comparator.naturalOrder() : Comparator.reverseOrder())))
                .collect(Collectors.toList());
    }

    private List<Car> sortByPrice(Order order) {
        return cars
                .stream()
                .sorted(Comparator.comparing(Car::getPrice, (order == Order.NATURAL ? Comparator.naturalOrder() : Comparator.reverseOrder())))
                .collect(Collectors.toList());
    }

    private List<Car> sortByColour(Order order) {
        return cars
                .stream()
                .sorted(Comparator.comparing(Car::getColor, (order == Order.NATURAL ? Comparator.naturalOrder() : Comparator.reverseOrder())))
                .collect(Collectors.toList());
    }

    List<Car> mileageMoreThan(Integer mileageMark) {
        if (mileageMark == null) {
            throw new MyException("ARGS ARE NULL");
        }
        return cars
                .stream()
                .filter(x -> x.getMileage() > mileageMark)
                .collect(Collectors.toList());
    }

    Map<Color, Long> eColorIntegerMap() {
        return cars
                .stream()
                .collect(Collectors.groupingBy(Car::getColor, Collectors.counting()))
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

    Map<String, Car> maxPriceModel() {
        return cars
                .stream()
                .collect(Collectors.groupingBy(Car::getModel))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue()
                                .stream()
                                .max(Comparator.comparing(Car::getPrice))
                                .orElseThrow(() -> new MyException("NIE UDALO SIE POBRAC DANYCH"))
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));
    }

    void statistics() {
        IntSummaryStatistics mileageStat = cars.stream().collect(Collectors.summarizingInt(Car::getMileage));

        BigDecimal minPrice = cars.stream().map(Car::getPrice).min(Comparator.comparing(Function.identity())).orElseThrow(() -> new MyException("MIN PRICE IS NULL"));
        BigDecimal maxPrice = cars.stream().map(Car::getPrice).max(Comparator.comparing(Function.identity())).orElseThrow(() -> new MyException("MAX PRICE IS NULL"));
        BigDecimal avgPrice = cars
                .stream()
                .map(Car::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(new BigDecimal(cars.size()), 2, BigDecimal.ROUND_FLOOR);

        System.out.println("Mileage:\nmin: " + mileageStat.getMin() + "\nmax: " + mileageStat.getMax() + "\naverage: " + mileageStat.getAverage());
        System.out.println("Price:\nmin: " + minPrice.toString() + "\nmax: " + maxPrice.toString() + "\naverage: " + avgPrice.toString());
    }

    List<Car> maxPriceCar() {
        final BigDecimal maxPrice = cars
                .stream()
                .max(Comparator.comparing(Car::getPrice))
                .map(Car::getPrice)
                .orElseThrow(() -> new MyException("MAX PRICE CAR IS NULL"));

        return cars
                .stream()
                .filter(x -> x.getPrice().compareTo(maxPrice) == 0)
                .collect(Collectors.toList());
    }

    List<Car> componentsAlphabeticSort() {
        return cars
                .stream()
                .peek(x -> x.setComponents(
                        x.getComponents()
                                .stream()
                                .sorted()
                                .collect(Collectors.toList())
                        )
                )
                .collect(Collectors.toList());
    }

    Map<String, List<Car>> componentsMap() {
        return cars
                .stream()
                .flatMap(x -> x.getComponents().stream())
                .collect(Collectors.toSet())
                .stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        x -> cars
                                .stream()
                                .filter(c -> c.getComponents().contains(x))
                                .collect(Collectors.toList())
                ))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(e -> e.getValue().size()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));
    }

    List<Car> rangePrice(BigDecimal min, BigDecimal max) {
        if (min == null || max == null) {
            throw new MyException("ARGS IS NULL");
        }
        return cars
                .stream()
                .filter(x -> x.getPrice().compareTo(min) >= 0 && x.getPrice().compareTo(max) <= 0)
                .sorted(Comparator.comparing(Car::getModel))
                .collect(Collectors.toList());

    }
}
