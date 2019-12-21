package pl.com.app.json;

import pl.com.app.model.Customer;

import java.util.List;

public class CustomersConverter extends JsonConverter<List<Customer>> {
    public CustomersConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
