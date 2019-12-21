package pl.com.app;

import pl.com.app.exceptions.MyException;
import pl.com.app.service.MenuService;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main(String[] args) {
        try {
            new MenuService().carsManagementSimulator();
        } catch (MyException e) {
            System.err.println(e.toString());
        }
    }
}
