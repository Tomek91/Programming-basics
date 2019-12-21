package pl.com.app.service;


import pl.com.app.exceptions.MyException;
import pl.com.app.model.Car;
import pl.com.app.model.enums.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

class CarsService {
    private final Set<Car> cars = new HashSet<>();
    private final DataLoaderService dataLoaderService = new DataLoaderService();

    CarsService(List<String> fileNames) {
        for (String fileName : fileNames) {
            cars.add(dataLoaderService.loadCar(fileName));
        }
    }

    public Set<Car> getCars() {
        return cars;
    }

    List<Car> customSort(SortBy sortBy, Order order) {
        if (sortBy == null || order == null) {
            throw new MyException("ARGS ARE NULL");
        }
        List<Car> carList = null;
        if (sortBy == SortBy.COMPONENTS_SIZE) {
            carList = sortByComponentsSize(order);
        } else if (sortBy == SortBy.WHEEL_SIZE) {
            carList = sortByWheelSize(order);
        } else {
            carList = sortByPower(order);
        }
        return carList;
    }

    private List<Car> sortByPower(Order order) {
        Comparator<Double> comparatorOrder = (order == Order.NATURAL ? Comparator.naturalOrder() : Comparator.reverseOrder());
        return cars
                .stream()
                .sorted(Comparator.comparing(x -> x.getEngine().getPower(), comparatorOrder))
                .collect(Collectors.toList());
    }

    private List<Car> sortByWheelSize(Order order) {
        Comparator<Integer> comparatorOrder = (order == Order.NATURAL ? Comparator.naturalOrder() : Comparator.reverseOrder());
        return cars
                .stream()
                .sorted(Comparator.comparing(x -> x.getWheel().getSize(), comparatorOrder))
                .collect(Collectors.toList());
    }

    private List<Car> sortByComponentsSize(Order order) {
        Comparator<Integer> comparatorOrder = (order == Order.NATURAL ? Comparator.naturalOrder() : Comparator.reverseOrder());
        return cars
                .stream()
                .sorted(Comparator.comparing(x -> x.getCarBody().getComponents().size(), comparatorOrder))
                .collect(Collectors.toList());
    }

    List<Car> carsWithPriceAndBodyType(CarBodyType bodyType, BigDecimal min, BigDecimal max) {
        if (bodyType == null || min == null || max == null) {
            throw new MyException("ARGS ARE NULL");
        }
        return cars
                .stream()
                .filter(x -> bodyType == x.getCarBody().getbType())
                .filter(x -> x.getPrice().compareTo(min) >= 0 && x.getPrice().compareTo(max) <= 0)
                .collect(Collectors.toList());
    }

    List<Car> carsWithEngineTypeAndAlphabeticSort(EngineType engineType) {
        if (engineType == null) {
            throw new MyException("ARGS ARE NULL");
        }
        return cars
                .stream()
                .filter(x -> engineType == x.getEngine().geteType())
                .sorted(Comparator.comparing(Car::getModel))
                .collect(Collectors.toList());
    }

    void statistics(ParamType paramType) {
        if (paramType == null) {
            throw new MyException("ARGS ARE NULL");
        }
        if (paramType == ParamType.MILEAGE) {
            IntSummaryStatistics mileageStatistics = cars
                    .stream()
                    .collect(Collectors.summarizingInt(Car::getMileage));
            System.out.println("MILEAGE");
            System.out.println("min: " + mileageStatistics.getMin());
            System.out.println("max: " + mileageStatistics.getMax());
            System.out.println("avg: " + new BigDecimal(mileageStatistics.getAverage()).setScale(2, BigDecimal.ROUND_CEILING));
        } else if (paramType == ParamType.POWER) {
            DoubleSummaryStatistics powerStatistics = cars
                    .stream()
                    .collect(Collectors.summarizingDouble(x -> x.getEngine().getPower()));
            System.out.println("POWER");
            System.out.println("min: " + powerStatistics.getMin());
            System.out.println("max: " + powerStatistics.getMax());
            System.out.println("avg: " + new BigDecimal(powerStatistics.getAverage()).setScale(2, BigDecimal.ROUND_CEILING));
        } else {
            System.out.println("PRICE");
            System.out.println("min: " + cars.stream().min(Comparator.comparing(Car::getPrice)).map(Car::getPrice).orElseThrow(NullPointerException::new));
            System.out.println("max: " + cars.stream().max(Comparator.comparing(Car::getPrice)).map(Car::getPrice).orElseThrow(NullPointerException::new));
            BigDecimal avgPrice = cars
                    .stream()
                    .map(Car::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(new BigDecimal(cars.size()), 2, RoundingMode.CEILING);
            System.out.println("avg: " + avgPrice);

        }
    }

    Map<Car, Integer> mileageCar() {
        return cars
                .stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        Car::getMileage
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new)
                );
    }

    Map<TyreType, List<Car>> tyreTypeMap() {
        return cars
                .stream()
                .collect(Collectors.groupingBy(x -> x.getWheel().getwType()))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(e -> e.getValue().size(), Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, LinkedHashMap::new)
                );
    }

    List<Car> carsContainsComponents(List<Component> components) {
        if (components == null) {
            throw new MyException("ARGS ARE NULL");
        }
        return cars
                .stream()
                .filter(x -> x.getCarBody().getComponents().containsAll(components))
                .sorted(Comparator.comparing(Car::getModel))
                .collect(Collectors.toList());
    }
}
