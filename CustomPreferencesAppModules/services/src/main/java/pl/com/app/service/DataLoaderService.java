package pl.com.app.service;

import pl.com.app.exceptions.MyException;
import pl.com.app.json.CustomersConverter;
import pl.com.app.json.PreferencesConverter;
import pl.com.app.json.ProductsConverter;
import pl.com.app.model.Customer;
import pl.com.app.model.Preference;
import pl.com.app.model.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataLoaderService {

    List<Customer> loadClients(String fileName){
        if (fileName == null){
            throw new MyException("FILENAME IS NULL");
        }
        CustomersConverter customersConverter = new CustomersConverter(fileName);
        return customersConverter.fromJson().orElseThrow(() -> new MyException("CUSTOMER PARSER EXCEPTION"));
    }

    Map<Integer, String> loadPreferences(String fileName){
        if (fileName == null){
            throw new MyException("FILENAME IS NULL");
        }
        PreferencesConverter preferencesConverter = new PreferencesConverter(fileName);
        List<Preference> preferences = preferencesConverter.fromJson().orElseThrow(() -> new MyException("PREFERENCES PARSER EXCEPTION"));
        Map<Integer, String> loadPreferences = new HashMap<>();
        for (Preference pref : preferences){
            loadPreferences.put(pref.getId(), pref.getName());
        }
        return loadPreferences;
    }

    List<Product> loadProducts(String fileName) {
        if (fileName == null) {
            throw new MyException("FILENAME IS NULL");
        }
        ProductsConverter productsConverter = new ProductsConverter(fileName);
        return productsConverter.fromJson().orElseThrow(() -> new MyException("PRODUCTS PARSER EXCEPTION"));
    }
}
