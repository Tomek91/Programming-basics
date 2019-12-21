package pl.com.app.parsers.movies;

import pl.com.app.parsers.properties.GetTimeProperty;
import pl.com.app.validations.CustomException;
import pl.com.app.validations.TimeValidationException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Programme {
    Map<LocalDate, Map<Screening, List<LocalTime>>> repMap = new HashMap<>();

    public Programme(String fileName, LocalDate dateMin, LocalDate dateMax) {
        setRepMap(generateProgramme(fileName, dateMin, dateMax));
    }

    private static Map<LocalDate, Map<Screening, List<LocalTime>>> generateProgramme(String fileName, LocalDate dateMin, LocalDate dateMax) {
        if (fileName == null || dateMin == null || dateMax == null) {
            throw new CustomException("ARGS IS NULL");
        }
        Map<LocalDate, Map<Screening, List<LocalTime>>> repMap = new LinkedHashMap<>();
        Screenings screenings = new Screenings(fileName);

        Map<String, String> propMap = new GetTimeProperty().getPropValues();
        int h = Integer.valueOf(propMap.get("gMax")) + 1;
        int m = Integer.valueOf(propMap.get("mMax")) + 1;
        Random r = new Random();
        while (dateMin.compareTo(dateMax) < 0) {
            Map<Screening, List<LocalTime>> mapSc = new LinkedHashMap<>();
            for (int i = 0; i < screenings.getScreenings().size(); i++) {
                List<LocalTime> time = new ArrayList<>();
                int N = r.nextInt(10) + 1;
                for (int j = 0; j < N; j++) {
                    try {
                        int hour = r.nextInt(h) + 1;
                        int minute = r.nextInt(m) + 1;
                        checkTime(hour, minute);
                        time.add(LocalTime.of(hour, minute));
                    } catch (CustomException e) {
                        CustomException.addPairToExceptionMap("WALIDACJA CZAS", e);
                    }
                }
                Collections.sort(time);
                mapSc.put(screenings.getScreenings().get(i), time);
            }
            repMap.put(dateMin, mapSc);
            dateMin = dateMin.plusDays(1L);
        }
        return repMap;
    }

    private static void checkTime(int h, int m) throws TimeValidationException {
        if (h < 0 || h > 23) {
            throw new TimeValidationException("godziny");
        }
        if (m < 0 || m > 59) {
            throw new TimeValidationException("minuty");
        }
    }

    public Map<LocalDate, Map<Screening, List<LocalTime>>> getRepMap() {
        return repMap;
    }

    public void setRepMap(Map<LocalDate, Map<Screening, List<LocalTime>>> repMap) {
        this.repMap = repMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Programme programme = (Programme) o;

        return repMap != null ? repMap.equals(programme.repMap) : programme.repMap == null;
    }

    @Override
    public int hashCode() {
        return repMap != null ? repMap.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Programme{" +
                "repMap=" + repMap +
                '}';
    }


}
