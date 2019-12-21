package pl.com.app.service;

import pl.com.app.exceptions.MyException;
import pl.com.app.models.Country;
import pl.com.app.models.TravelAgency;
import pl.com.app.models.Trip;
import pl.com.app.parsers.CountriesConverter;
import pl.com.app.parsers.TravelAgencyConverter;
import pl.com.app.parsers.TripsConverter;

import java.util.List;

class DataLoaderService {

    List<Trip> loadTrips(String fileName) {
        if (fileName == null) {
            throw new MyException("FILENAME IS NULL");
        }
        return new TripsConverter(fileName)
                .fromJson()
                .orElseThrow(() -> new MyException("TRIPS CONVERTER IS NULL"));
    }

    List<Country> loadCountries(String fileName) {
        if (fileName == null) {
            throw new MyException("FILENAME IS NULL");
        }
        return new CountriesConverter(fileName)
                .fromJson()
                .orElseThrow(() -> new MyException("COUNTRY CONVERTER IS NULL"));
    }

    List<TravelAgency> loadTravelAgency(String fileName) {
        if (fileName == null) {
            throw new MyException("FILENAME IS NULL");
        }
        return new TravelAgencyConverter(fileName)
                .fromJson()
                .orElseThrow(() -> new MyException("TRAVEL AGENCY CONVERTER IS NULL"));
    }
}