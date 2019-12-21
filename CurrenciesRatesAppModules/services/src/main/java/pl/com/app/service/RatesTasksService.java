package pl.com.app.service;


import org.eclipse.collections.impl.collector.Collectors2;
import pl.com.app.exceptions.MyException;
import pl.com.app.model.CurrencyModel;
import pl.com.app.model.Rate;
import pl.com.app.reader.DataReader;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class RatesTasksService {

    List<String> threeMaxGrowth(LocalDate dateFromUser) {
        if (dateFromUser == null) {
            throw new NullPointerException("DATE IS NULL");
        }
        return generateGrowthCurrencyModel(2, dateFromUser)
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    List<Rate> startWithUserExpression(LocalDate dateFromUser, String currencyStartWith) {
        if (dateFromUser == null || currencyStartWith == null) {
            throw new MyException("ARGUMENTS IS NULL");
        }
        CurrencyModel curModelUserDay = LoadCurrencyService.generateCurrencyModel(dateFromUser.toString());

        return Stream.of(curModelUserDay)
                .flatMap(x -> Arrays.stream(x.getRates()))
                .filter(x -> x.getCurrency().startsWith(currencyStartWith))
                .collect(Collectors.toList());
    }

    String dontGrowth(LocalDate dateFromUser) {
        if (dateFromUser == null) {
            throw new NullPointerException("DATE IS NULL");
        }
        return generateGrowthCurrencyModel(2, dateFromUser)
                .entrySet()
                .stream()
                .filter(e -> e.getValue().compareTo(BigDecimal.ZERO) <= 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.joining(", "));
    }

    String growthPercentCurrency(LocalDate dateFromUser, BigDecimal min, BigDecimal max) {
        if (dateFromUser == null || min == null || max == null) {
            throw new MyException("ARGUMENTS ARE NULL");
        }

        Map<String, BigDecimal> percentCurrency = generateCurrencyList(2, dateFromUser)
                .stream()
                .flatMap(x -> Arrays.stream(x.getRates()))
                .collect(Collectors.groupingBy(Rate::getCode))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue()
                                .stream()
                                .map(Rate::getMid)
                                .reduce(BigDecimal::subtract)
                                .orElseThrow(() -> new MyException("GENERATE GROWTH CURRENCY MODEL EXCEPTION"))
                                .multiply(new BigDecimal(-100))
                ));

        return percentCurrency
                .entrySet()
                .stream()
                .filter(e -> e.getValue().compareTo(BigDecimal.ZERO) > 0)
                .filter(e -> e.getValue().compareTo(min) >= 0 && e.getValue().compareTo(max) <= 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.joining(", "));
    }

    private List<CurrencyModel> generateCurrencyList(Integer N, LocalDate dateFromUser) {
        if (N == null || dateFromUser == null) {
            throw new MyException("ITEMS IS NULL");
        }
        List<CurrencyModel> currencyModelList = new ArrayList<>();
        LocalDate date = LocalDate.of(dateFromUser.getYear(), dateFromUser.getMonth(), dateFromUser.getDayOfMonth());
        while (currencyModelList.size() < N) {
            date = dateExcludeWeekend(date);
            currencyModelList.add(LoadCurrencyService.generateCurrencyModel(date.toString()));
            date = date.minusDays(1);
        }
        return currencyModelList;
    }

    private LocalDate dateExcludeWeekend(LocalDate date) {
        if (date == null) {
            throw new MyException("DATE IS NULL");
        }
        while (date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            date = date.minusDays(1);
        }
        return date;
    }

    private Map<String, BigDecimal> generateGrowthCurrencyModel(Integer N, LocalDate dateFromUser) {
        if (N == null || dateFromUser == null) {
            throw new MyException("ITEMS IS NULL");
        }
        return generateCurrencyList(N, dateFromUser)
                .stream()
                .flatMap(x -> Arrays.stream(x.getRates()))
                .collect(Collectors.groupingBy(Rate::getCode))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue()
                                .stream()
                                .map(Rate::getMid)
                                .reduce(BigDecimal::subtract)
                                .orElseThrow(() -> new MyException("GENERATE GROWTH CURRENCY MODEL EXCEPTION")),
                        (v1, v2) -> v1
                ));
    }

    List<String> maxGrowthAndAvgRate(LocalDate dateFromUser) {
        if (dateFromUser == null) {
            throw new NullPointerException("DATE IS NULL");
        }

        List<String> maxGrowthAndAvgRate = new ArrayList<>();
        final String maxGrowthCode = generateGrowthCurrencyModel(2, dateFromUser)
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new MyException("MAX GROWTH CODE EXCEPTION"));

        String avgCurrencyIn10Days = generateCurrencyList(10, dateFromUser)
                .stream()
                .flatMap(x -> Arrays.stream(x.getRates()))
                .filter(x -> x.getCode().equals(maxGrowthCode))
                .map(Rate::getMid)
                .collect(Collectors2.summarizingBigDecimal(x -> x))
                .getAverage()
                .setScale(4, RoundingMode.CEILING)
                .toString();

        maxGrowthAndAvgRate.add(maxGrowthCode);
        maxGrowthAndAvgRate.add(avgCurrencyIn10Days);
        return maxGrowthAndAvgRate;
    }

    Map<BigDecimal, List<String>> avgGrowthIn10Price(LocalDate dateFromUser) {
        if (dateFromUser == null) {
            throw new MyException("DATE IS NULL");
        }

        Map<String, BigDecimal> avgGrowthIn10Price = new HashMap<>();

        generateCurrencyList(10, dateFromUser)
                .stream()
                .flatMap(x -> Arrays.stream(x.getRates()))
                .collect(Collectors.groupingBy(Rate::getCode))
                .forEach((k, v) -> {
                    BigDecimal avgGrowth = BigDecimal.ZERO;
                    for (int i = 1; i < v.size(); i++) {
                        avgGrowth = avgGrowth.add(v.get(i - 1).getMid().subtract(v.get(i).getMid()));
                    }
                    avgGrowthIn10Price.put(k, avgGrowth.divide(new BigDecimal(v.size()), 4, RoundingMode.HALF_UP));
                });

         return avgGrowthIn10Price
                .entrySet()
                .stream()
                .collect(Collectors.groupingBy(Map.Entry::getValue))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream().map(Map.Entry::getKey).collect(Collectors.toList())
                ));
    }

    List<String> theBestInvestment(LocalDate dateFromUser) {
        if (dateFromUser == null) {
            throw new MyException("DATE IS NULL");
        }

        return generateCurrencyList(10, dateFromUser)
                .stream()
                .flatMap(x -> Arrays.stream(x.getRates()))
                .collect(Collectors.groupingBy(Rate::getCode))
                .entrySet()
                .stream()
                .filter(e -> checkCurrencyGrowth(e.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private boolean checkCurrencyGrowth(List<Rate> rateList) {
        if (rateList == null) {
            throw new MyException("RATE LIST IS NULL");
        }
        int numberOfDrops = 0;
        for (int i = 1; i < rateList.size(); i++) {
            if ((rateList.get(i - 1).getMid()).subtract(rateList.get(i).getMid()).compareTo(BigDecimal.ZERO) >= 0
                    && ++numberOfDrops > 2) {
                return false;
            }
        }
        return true;
    }

    public static LocalDate dateFromUser() {
        System.out.println("podaj date sprawdzenia kursów walut");
        LocalDate localDate = new DataReader().getLocalDate();
        return LocalDate.parse(localDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }
}
