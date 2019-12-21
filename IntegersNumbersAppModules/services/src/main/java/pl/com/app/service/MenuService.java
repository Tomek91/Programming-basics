package pl.com.app.service;

import pl.com.app.parsers.FileNames;
import pl.com.app.reader.DataReader;
import pl.com.app.service.utils.MenuItem;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MenuService {
    static final String PREFIX = "<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>";
    private ListsService listsService;

    public void integerNumbersApp() {
        System.out.println("Witaj w symulatorze przetwarzania liczb !!!");
        System.out.println("Oto Twoje menu. Każda liczba odpowiada akcji, którą chcesz wykonać.\n" +
                "Pamiętaj, w każdej chwili możesz zakończyć aplikację naciskając 'q'.");

        listsService = new ListsService(FileNames.NUMBERS);
        RetrieveDataService.retrieveMenu(createMenu(), this::retrieveMainData);
    }

    private void retrieveMainData(String data) {
        switch (data) {
            case "1": {
                isPerfectFile();
                break;
            }
            case "2": {
                mapOfNumbersToRemoveToBePerfectFile();
                break;
            }
            case "3": {
                diffBetweenMaxListOneAndMinListTwo();
                break;
            }
            case "4": {
                numberOfDividingByDifference();
                break;
            }
            case "5": {
                longestDecreaseCourse();
                break;
            }
            case "6": {
                minMaxDiffAfterSorting();
                break;
            }
            case "7": {
                showLists();
                break;
            }
            case "q": {
                System.out.println("Koniec programu.");
                DataReader.close();
                break;
            }
            default:
                System.out.println("Niepopoprawny kod menu. Spróbuj jeszcze raz.");
        }
    }

    private void showLists() {
        System.out.println("Min list");
        System.out.println(listsService.getMinList());
        System.out.println("Avg list");
        System.out.println(listsService.getMidList());
        System.out.println("Max list");
        System.out.println(listsService.getMaxList());
    }

    private void minMaxDiffAfterSorting() {
        listsService.minMaxDiffAfterSorting();
    }

    private void longestDecreaseCourse() {
        String longestDecreaseCourse = listsService.longestDecreaseCourse();
        System.out.println("longestDecreaseCourse " + longestDecreaseCourse);
    }

    private void numberOfDividingByDifference() {
        int numberOfDividingByDifference = listsService.numberOfDividingByDifference();
        System.out.println("numberOfDividingByDifference " + numberOfDividingByDifference);
    }

    private void diffBetweenMaxListOneAndMinListTwo() {
        int diffBetweenMaxListOneAndMinListTwo = listsService.diffBetweenMaxListOneAndMinListTwo();
        System.out.println("diffBetweenMaxListOneAndMinListTwo " + diffBetweenMaxListOneAndMinListTwo);
    }

    private void mapOfNumbersToRemoveToBePerfectFile() {
        Map<String, List<Integer>> mapOfNumbersToRemoveToBePerfectFile = listsService.mapOfNumbersToRemoveToBePerfectFile();
        mapOfNumbersToRemoveToBePerfectFile.forEach((k, v) -> System.out.println(k + " " + v));
    }

    private void isPerfectFile() {
        boolean isPerfectFile = listsService.isPerfectFile();
        System.out.println("isPerfectFile... " + isPerfectFile);
    }

    private List<MenuItem> createMenu() {
        return Arrays.asList(
                MenuItem.builder().code("1").name("sprawdź czy plik jest plikiem doskonałym").build(),
                MenuItem.builder().code("2").name("które liczby z listy pierwszej oraz które liczby z listy drugiej należałoby usunąć, żeby zaszedł warunek na plik doskonały?").build(),
                MenuItem.builder().code("3").name("różnica pomiędzy największym elementem pierwszej listy oraz najmniejszym elementem drugiej listy").build(),
                MenuItem.builder().code("4").name("ile elementów w trzeciej liście dzieli się przez tak wyznaczoną różnicę, o ile nie jest ona zerowa").build(),
                MenuItem.builder().code("5").name("wyznacz tą z trzech list w klasie Listy, która posiada w sobie najdłuższy ciąg niemalejący utworzony z jej elementów").build(),
                MenuItem.builder().code("6").name("posortuj trzy listy malejąco i sprawdź, dla jakiego indeksu elementów list różnica pomiędzy elementami trzech list jest najmniejsza, a dla jakiego indeksu różnica pomiędzy elementami trzech list jest największa").build(),
                MenuItem.builder().code("7").name("pokaz zestawienie liczb").build(),
                MenuItem.builder().code("q").name("koniec aplikacji").build()
        );
    }
}
