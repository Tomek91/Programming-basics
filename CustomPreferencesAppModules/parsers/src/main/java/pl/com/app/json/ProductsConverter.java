package pl.com.app.json;

import pl.com.app.model.Product;

import java.util.List;

public class ProductsConverter extends JsonConverter<List<Product>> {
    public ProductsConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
