package pl.com.app.parsers.json;

import pl.com.app.models.OrdersModel;

public class OrdersModelConverter extends JsonConverter<OrdersModel> {
    public OrdersModelConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
