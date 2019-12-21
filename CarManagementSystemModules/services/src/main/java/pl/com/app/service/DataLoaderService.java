package pl.com.app.service;

import pl.com.app.exceptions.MyException;
import pl.com.app.model.Car;
import pl.com.app.parsers.json.CarsConverter;

import java.util.List;

class DataLoaderService {

    List<Car> loadCarsManagement(String fileName) {
        if (fileName == null) {
            throw new MyException("FILE NAME IS NULL");
        }
        return new CarsConverter(fileName)
                .fromJson()
                .orElseThrow(() -> new MyException("CAR MANAGEMENT CONVERTER EXCEPTION"))
                .getCars();
    }
}
