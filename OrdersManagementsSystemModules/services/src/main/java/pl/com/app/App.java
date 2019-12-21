package pl.com.app;

import pl.com.app.exceptions.MyException;
import pl.com.app.service.MenuService;

/**
 * 03_STRUMIENIE.pdf
 */
public class App {
    public static void main(String[] args) {
        try {
            new MenuService().ordersApp();
        } catch (MyException e) {
            System.err.println(e.toString());
        }
    }
}
