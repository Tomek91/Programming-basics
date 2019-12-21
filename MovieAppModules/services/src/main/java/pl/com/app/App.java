package pl.com.app;

import pl.com.app.service.MenuService;
import pl.com.app.validations.CustomException;

public class App{
    public static void main(String[] args) {
        try {
            new MenuService().movieApp();
        } catch (CustomException e) {
            System.err.println(e.toString());
        }
    }

}
