package pl.com.app.service;

import pl.com.app.reader.DataReader;
import pl.com.app.service.utils.MenuItem;

import java.util.List;

@FunctionalInterface
public interface RetrieveDataService {
    void retrieveData(String data);

    static void retrieveMenu(List<MenuItem> menuItems, RetrieveDataService retrieveDataService){
        String data = null;
        do {
            data = doRetrieving(menuItems, retrieveDataService);
        } while (!data.equalsIgnoreCase("q"));
    }

    static String doRetrieving(List<MenuItem> menuItems, RetrieveDataService retrieveDataService){
        String data = null;
        System.out.println(MenuService.PREFIX);
        System.out.println("Wybierz pozycję menu...");
        System.out.println(MenuItem.showMenu(menuItems));
        data = new DataReader().getString();
        retrieveDataService.retrieveData(data);
        return data;
    }
}
