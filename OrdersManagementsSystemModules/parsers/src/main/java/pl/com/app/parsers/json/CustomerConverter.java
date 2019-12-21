package pl.com.app.parsers.json;

import pl.com.app.models.Customer;

import java.util.List;

public class CustomerConverter extends JsonConverter<List<Customer>> {
    public CustomerConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
