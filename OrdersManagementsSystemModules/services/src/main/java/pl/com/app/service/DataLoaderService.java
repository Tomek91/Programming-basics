package pl.com.app.service;

import pl.com.app.exceptions.MyException;
import pl.com.app.models.Order;
import pl.com.app.parsers.json.OrdersModelConverter;

import java.util.List;

class DataLoaderService {

    List<Order> loadOrdersModel(String fileName) {
        if (fileName == null) {
            throw new MyException("FILENAME IS NULL");
        }
        return new OrdersModelConverter(fileName)
                .fromJson()
                .orElseThrow(() -> new MyException("ORDERS CONVERTER IS NULL"))
                .getOrders();
    }
}