package pl.com.app.service;

import pl.com.app.exceptions.MyException;
import pl.com.app.model.Car;
import pl.com.app.parsers.json.CarConverter;

class DataLoaderService {

    Car loadCar(String fileName) {
        if (fileName == null) {
            throw new MyException("FILENAME IS NULL");
        }
        CarConverter carConverter = new CarConverter(fileName);
        return new Car(carConverter.fromJson().orElseThrow(() -> new MyException("CAR PARSER EXCEPTION")));
    }
}