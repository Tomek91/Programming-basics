package pl.com.app.service;

import pl.com.app.exceptions.MyException;
import pl.com.app.json.CustomerOrderConverter;
import pl.com.app.models.CustomerOrder;

import java.util.List;

class DataLoaderService {

    List<CustomerOrder> loadCustomerOrders(String fileName) {
        if (fileName == null) {
            throw new MyException("FILE NAME IS NULL");
        }
        return new CustomerOrderConverter(fileName)
                .fromJson()
                .orElseThrow(() -> new MyException("CUSTOMER ORDERS PARSER EXCEPTION"));
    }
}
