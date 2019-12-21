package pl.com.app.service;


import org.eclipse.collections.impl.collector.Collectors2;
import pl.com.app.exceptions.MyException;
import pl.com.app.models.Country;
import pl.com.app.models.TravelAgency;
import pl.com.app.models.Trip;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


class DealService {
    private final Map<TravelAgency, Set<Trip>> deals;
    private DataLoaderService dataLoaderService = new DataLoaderService();

    DealService(String fileName) {
        deals = generateDeals(fileName);
    }

    public Map<TravelAgency, Set<Trip>> getDeals() {
        return deals;
    }

    private Map<TravelAgency, Set<Trip>> generateDeals(String fileName) {
        if (fileName == null) {
            throw new MyException("FILENAME IS NULL");
        }

        List<TravelAgency> travelAgencyList = dataLoaderService.loadTravelAgency(fileName);
        TripService tripService = new TripServiceImpl();
        return tripService.findAll()
                .stream()
                .collect(Collectors.groupingBy(Trip::getTravel_agency_id))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> travelAgencyList
                                .stream()
                                .filter(x -> x.getId() == e.getKey())
                                .findFirst()
                                .orElse(TravelAgency
                                        .builder()
                                        .id(e.getKey())
                                        .country("XXX")
                                        .name("PIRACI")
                                        .build()
                                ),
                        e -> new HashSet<>(e.getValue())
                ));
    }

    List<TravelAgency> travelAgencyTheMostTrips() {
        Map<TravelAgency, Integer> travelAgencyTripsSize = deals
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().size()
                ));

        final Integer maxSize = travelAgencyTripsSize
                .values()
                .stream()
                .max(Comparator.naturalOrder())
                .orElseThrow(() -> new MyException("MOST TRIPS EXCEPTION"));

        return travelAgencyTripsSize
                .entrySet()
                .stream()
                .filter(e -> e.getValue() == maxSize)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    TravelAgency theMostEarnTravelAgency() {
        return deals
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue()
                                .stream()
                                .map(x -> x.getPrice()
                                        .multiply(new BigDecimal(x.getPeople_number()))
                                        .multiply(new BigDecimal(0.1 * 0.81)))
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                ))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new MyException("MOST EARN AGENCY EXCEPTION"));
    }

    String theMostFrequencyCountry() {
        return deals
                .values()
                .stream()
                .flatMap(Set::stream)
                .collect(Collectors.groupingBy(Trip::getDestination, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new MyException("MOST FREQ COUNTRY EXCEPTION"));
    }

    Map<TravelAgency, BigDecimal> avgPriceByTravelAgency() {
        return deals
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue()
                                .stream()
                                .collect(Collectors2.summarizingBigDecimal(Trip::getPrice))
                                .getAverage().setScale(2, BigDecimal.ROUND_CEILING)
                ));
    }

    Map<TravelAgency, Trip> theLowestDiffPriceBetweenAvg() {
        Map<TravelAgency, Trip> theLowestDiffPriceBetweenAvg = new HashMap<>();
        this.avgPriceByTravelAgency().forEach((k, v) ->
                theLowestDiffPriceBetweenAvg.put(k, deals.get(k)
                        .stream()
                        .min(Comparator.comparing(x -> x.getPrice().subtract(v).abs()))
                        .orElseThrow(() -> new MyException("THE LOWEST DIFF EXCEPTION")))
        );
        return theLowestDiffPriceBetweenAvg;
    }

    Map<String, TravelAgency> theMostAgencyInCountry() {
        Map<String, Map<TravelAgency, Integer>> agencyInCountry = new HashMap<>();
        deals.forEach((k, v) ->
                v.forEach(x -> {
                    Map<TravelAgency, Integer> numberOfAgency = new HashMap<>();
                    if (agencyInCountry.containsKey(x.getDestination())) {
                        numberOfAgency = agencyInCountry.get(x.getDestination());
                    }

                    Integer numberOfAgencyValue = 1;
                    if (numberOfAgency.containsKey(k)) {
                        numberOfAgencyValue += numberOfAgency.get(k);
                    }

                    numberOfAgency.put(k, numberOfAgencyValue);
                    agencyInCountry.put(x.getDestination(), numberOfAgency);
                })
        );

        return agencyInCountry
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue()
                                .entrySet()
                                .stream()
                                .max(Map.Entry.comparingByValue())
                                .map(Map.Entry::getKey)
                                .orElseThrow(() -> new MyException("MOST AGENCY IN COUNTRY EXCEPTION"))
                ));
    }

    List<Trip> europeanTrips(final String fileName) {
        if (fileName == null) {
            throw new MyException("FILENAME NULL");
        }
        List<String> europeanCountries = dataLoaderService.loadCountries(fileName)
                .stream()
                .map(Country::getName)
                .collect(Collectors.toList());

        return deals
                .values()
                .stream()
                .flatMap(Set::stream)
                .filter(x -> europeanCountries.contains(x.getDestination()))
                .collect(Collectors.toList());
    }

    Map<Integer, List<Trip>> numberOfPeopleInTrips() {
        return deals
                .values()
                .stream()
                .flatMap(Set::stream)
                .collect(Collectors.toMap(
                        Trip::getPeople_number,
                        Arrays::asList,
                        (v1, v2) -> Stream.of(v1, v2)
                                .flatMap(List::stream)
                                .collect(Collectors.toList()),
                        LinkedHashMap::new
                ));
    }

    Map<Integer, Trip> numberOfPeopleInMaxPriceTrip() {

        return numberOfPeopleInTrips()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue()
                                .stream()
                                .max(Comparator.comparing(Trip::getPrice))
                                .orElseThrow(() -> new MyException("MAX PRICE TRIP IS NULL"))
                ))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(e -> e.getValue().getPrice()
                        .divide(new BigDecimal(e.getValue().getPeople_number()), 2, BigDecimal.ROUND_CEILING), Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new)
                );
    }

    public static <T, R> void showItems(Map<T, R> itmes) {
        if (itmes == null) {
            throw new MyException("ITEMS ARE NULL");
        }
        itmes.forEach((k, v) -> System.out.println(k + " " + v));
    }
}
