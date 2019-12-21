package pl.com.app.service;

import pl.com.app.bank.DebitCard;
import pl.com.app.parsers.json.FileNames;
import pl.com.app.reader.DataReader;
import pl.com.app.service.utils.MenuItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MenuService {
    static final String PREFIX = "<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>";
    private DataReader dataReader = new DataReader();
    private BankOperationService bankOperationService;

    public void bankTransactionsSimulator() {
        System.out.println("Witaj w symulatorze operacji bankowych !!!");
        System.out.println("Oto Twoje menu. Każda liczba odpowiada akcji, którą chcesz wykonać.\n" +
                "Pamiętaj, w każdej chwili możesz zakończyć aplikację naciskając 'q'.");

        bankOperationService = new BankOperationService(FileNames.DEBIT_CARDS);
        RetrieveDataService.retrieveMenu(createMenu(), this::retrieveMainData);
    }

    private void retrieveMainData(String data) {
        switch (data) {
            case "1": {
                logInAccount();
                break;
            }
            case "2": {
                try {
                    addNewBankAccount();
                } catch (Exception e) {
                    System.err.println(e.toString());
                    return;
                }
            }
            case "3": {
                theLowestErrorsLoginObject();
                break;
            }
            case "4": {
                higherAccountBalance();
                break;
            }
            case "5": {
                statistics();
                break;
            }
            case "6": {
                loadDataFromJson();
                break;
            }
            case "7": {
                showAllDebitCards();
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

    private void showAllDebitCards() {
        bankOperationService.getDebitCards().forEach(System.out::println);
    }

    private void loadDataFromJson() {
        BankOperationService bankOperationServiceJson = new BankOperationService(FileNames.DEBIT_CARDS);
        bankOperationService.getDebitCards().clear();
        bankOperationService.getDebitCards().addAll(bankOperationServiceJson.getDebitCards());
    }

    private void statistics() {
        System.out.println("statistics");
        bankOperationService.debitCardsStats();
    }

    private void higherAccountBalance() {
        DebitCard debitCardOne = bankOperationService.getDebitCards().get(0);
        DebitCard debitCardTwo = bankOperationService.getDebitCards().get(1);
        DebitCard debitCardHigher = bankOperationService.higherBankBalance(debitCardOne, debitCardTwo);
        System.out.println("higherBankBalance between" + debitCardOne + " " + debitCardTwo);
        System.out.println("debitCardHigher" + debitCardHigher);
    }

    private void theLowestErrorsLoginObject() {
        DebitCard debitCard = bankOperationService.theLowestErrorsLogin();
        System.out.println("theLowestErrorsLogin " + debitCard);
    }

    private void addNewBankAccount() {
        System.out.println("Proszę podać pin do konta");
        String pin = dataReader.getString();
        System.out.println("Proszę podać stan konta");
        BigDecimal bankBalance = dataReader.getBigDecimal();
        bankOperationService.getDebitCards().add(new DebitCard(bankBalance, pin, new ArrayList<>()));
    }

    private void logInAccount() {
        System.out.println("Podaj pin");
        String pin = dataReader.getString();
        Optional<DebitCard> debitCardOptional = bankOperationService.verifyPin(pin);
        if (debitCardOptional.isPresent()) {
            DebitCard debitCard = debitCardOptional.get();
            RetrieveDataService.retrieveMenu(createLogInMenu(), x -> retrieveLogInData(x, debitCard));
        } else {
            System.err.println("ERROR - NIEPOPRAWNY PIN");
        }
    }

    private void retrieveLogInData(String data, DebitCard debitCard) {
        switch (data) {
            case "u": {
                try {
                    payment(debitCard);
                    break;
                } catch (Exception e) {
                    System.err.println(e.toString());
                    return;
                }
            }
            case "v": {
                try {
                    payout(debitCard);
                    break;
                } catch (Exception e) {
                    System.err.println(e.toString());
                    return;
                }
            }
            case "w": {
                bankBalance(debitCard);
                break;
            }
            case "x": {
                try {
                    checkHistoricalBankBalance(debitCard);
                    break;
                } catch (Exception e) {
                    System.err.println(e.toString());
                    return;
                }
            }
            case "y": {
                cancelTransactionsNumber(debitCard);
                break;
            }
            case "z": {
                clearHistoryOfTransactions(debitCard);
                break;
            }
            case "q": {
                System.out.println("wyjście z menu");
                break;
            }
            default:
                System.out.println("Niepopoprawny kod menu. Spróbuj jeszcze raz.");
        }
    }

    private void clearHistoryOfTransactions(DebitCard debitCard) {
        bankOperationService.clearHistoryOfTransactions(debitCard);
        System.out.println("wyczyszczono historię transakcji");
    }

    private void cancelTransactionsNumber(DebitCard debitCard) {
        Integer cancelTransactionsNumber = bankOperationService.cancelTransactionsNumber(debitCard);
        System.out.println("cancelTransactionsNumber " + cancelTransactionsNumber);
    }

    private void checkHistoricalBankBalance(DebitCard debitCard) {
        System.out.println("sprzed ilu operacji chcesz sprawdzić stan konta?");
        int N = dataReader.getInteger();
        BigDecimal historicalBankBalance = bankOperationService.checkHistoricalBankBalance(debitCard, N);
        System.out.println("historicalBankBalance " + historicalBankBalance);
    }

    private void bankBalance(DebitCard debitCard) {
        BigDecimal bankBalance = bankOperationService.bankBalance(debitCard);
        System.out.println(bankBalance);
    }

    private void payout(DebitCard debitCard) {
        System.out.println("ile chcesz wypłacić pieniędzy?");
        BigDecimal cash = dataReader.getBigDecimal();
        bankOperationService.payout(debitCard, cash);
    }

    private void payment(DebitCard debitCard) {
        System.out.println("ile chcesz wpłacić pieniędzy?");
        BigDecimal cash = dataReader.getBigDecimal();
        bankOperationService.payment(debitCard, cash);
    }

    private List<MenuItem> createMenu() {
        return Arrays.asList(
                MenuItem.builder().code("1").name("zaloguj sie na konto").build(),
                MenuItem.builder().code("2").name("dodaj konto bankowe").build(),
                MenuItem.builder().code("3").name("metoda przyjmuje jako argument tablicę obiektów klasy KartaPlatnicza i zwraca ten obiekt, który posiada najmniej wpisów BŁĄD_LOGOWANIA w tablicy historii operacji. Dodatkowo obiekt ten przed zwróceniem dostaje z tego tytułu bonus – stan konta zostaje powiększony o 0.5% stanu aktualnego,").build(),
                MenuItem.builder().code("4").name("metoda przyjmuje jako argument dwa obiekty klasy KartaPlatnicza i zwraca ten obiekt, który posiada większy stan konta").build(),
                MenuItem.builder().code("5").name("metoda przyjmuje jako argument tablicę obiektów klasy KartaPlatnicza i dla każdego obiektu wypisuje stan konta oraz zestawienie w postaci:\n" +
                        "WPŁATA – ILOŚĆ WYSTĄPIEŃ W HISTORII OPERACJI,\n" +
                        "WYPŁATA – ILOŚĆ WYSTĄPIEŃ W HISTORII OPERACJI,\n" +
                        "ANULOWANA – ILOŚĆ WYSTĄPIEŃ W HISTORII OPERACJI,\n" +
                        "BŁĄD_LOGOWANIA – ILOŚĆ WYSTĄPIEŃ W HISTORII OPERACJI").build(),
                MenuItem.builder().code("6").name("załaduj dane z pliku json").build(),
                MenuItem.builder().code("7").name("pokaż dane wszystkich kont").build(),
                MenuItem.builder().code("q").name("wyjdz").build()
        );
    }

    private List<MenuItem> createLogInMenu() {
        return Arrays.asList(

                MenuItem.builder().code("u").name("wpłać pieniądze").build(),
                MenuItem.builder().code("v").name("wypłać pieniądze").build(),
                MenuItem.builder().code("w").name("sprawdź stan konta").build(),
                MenuItem.builder().code("x").name("sprawdz stan konta sprzed operacji").build(),
                MenuItem.builder().code("y").name("ilość anulowanych operacji").build(),
                MenuItem.builder().code("z").name("wyczyść historię transakcji").build(),
                MenuItem.builder().code("q").name("wyjdz").build()
        );
    }
}
