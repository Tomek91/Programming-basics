package pl.com.app.json;

import pl.com.app.models.CustomerOrder;

import java.util.List;

public class CustomerOrderConverter extends JsonConverter<List<CustomerOrder>> {
    public CustomerOrderConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
