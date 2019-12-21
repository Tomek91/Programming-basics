package pl.com.app.service;

import pl.com.app.exceptions.MyException;
import pl.com.app.model.IntegerModel;
import pl.com.app.parsers.IntegerConverter;

import java.util.List;

class DataLoaderService {

    List<IntegerModel> loadIntegerModels(String fileName) {
        if (fileName == null) {
            throw new MyException("FILENAME IS NULL");
        }
        return new IntegerConverter(fileName)
                .fromJson()
                .orElseThrow(() -> new MyException("NUMBERS PARSER EXCEPTION"));
    }
}