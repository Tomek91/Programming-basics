package pl.com.app.service;


import pl.com.app.bank.AbstractBankOperation;
import pl.com.app.bank.DebitCard;
import pl.com.app.bank.enums.Operation;
import pl.com.app.exceptions.MyException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

class BankOperationService {
    private final List<DebitCard> debitCards = new ArrayList<>();
    private final DataLoaderService dataLoaderService = new DataLoaderService();

    BankOperationService(String fileName) {
        if (fileName == null) {
            throw new MyException("FILE NAME IS NULL");
        }
        this.debitCards.addAll(dataLoaderService.loadDebitCards(fileName));
    }

    public List<DebitCard> getDebitCards() {
        return debitCards;
    }

    DebitCard theLowestErrorsLogin() {
        DebitCard minErrors = debitCards
                .stream()
                .min(Comparator.comparing(AbstractBankOperation::cancelTransactionsNumber))
                .orElseThrow(() -> new MyException("THE LOWEST ERRORS LOGIN EXCEPTION"));
        minErrors.setBankBalance(minErrors.getBankBalance().multiply(new BigDecimal(1.05)).setScale(2, RoundingMode.CEILING));
        return minErrors;
    }

    DebitCard higherBankBalance(DebitCard debitCard1, DebitCard debitCard2) {
        if (debitCard1 == null || debitCard2 == null) {
            throw new MyException("DEBIT CARDS ARE NULL");
        }
        return debitCard1.getBankBalance().compareTo(debitCard2.getBankBalance()) >= 0 ? debitCard1 : debitCard2;
    }

    void debitCardsStats() {
        for (int i = 0; i < debitCards.size(); i++) {
            System.out.println("DebitCard nr " + i);
            System.out.println("-----------------------------------------------------------");
            System.out.println("bankBalance " + debitCards.get(i).getBankBalance());
            System.out.println(Operation.WPLATA.getCode() + " " + operationCount(Operation.WPLATA.getCode(), debitCards.get(i)));
            System.out.println(Operation.WYPLATA.getCode() + " " + operationCount(Operation.WYPLATA.getCode(), debitCards.get(i)));
            System.out.println(Operation.ANULOWANA.getCode() + " " + operationCount(Operation.ANULOWANA.getCode(), debitCards.get(i)));
            System.out.println(Operation.BLAD_LOGOWANIA.getCode() + " " + operationCount(Operation.BLAD_LOGOWANIA.getCode(), debitCards.get(i)));
            System.out.println("-----------------------------------------------------------");
        }
    }

    private static int operationCount(final String operation, DebitCard debitCard) {
        if (operation == null || debitCard == null) {
            throw new MyException("ARGS ARE NULL");
        }
        return (int) debitCard.getOperations()
                .stream()
                .filter(x -> x.equals(operation) || x.startsWith(operation))
                .count();
    }

    Optional<DebitCard> verifyPin(String pin) {
        if (pin == null) {
            throw new MyException("PIN IS NULL");
        }
        return debitCards
                .stream()
                .filter(x -> x.getPin().equals(pin))
                .findFirst();
    }

    void payment(DebitCard debitCard, BigDecimal cash) {
        if (debitCard == null || cash == null) {
            throw new MyException("ARGS ARE NULL");
        }
        debitCard.payment(cash);
    }

    void payout(DebitCard debitCard, BigDecimal cash) {
        if (debitCard == null || cash == null) {
            throw new MyException("ARGS ARE NULL");
        }
        debitCard.payout(cash);
    }

    BigDecimal bankBalance(DebitCard debitCard) {
        return debitCard.getBankBalance();
    }

    BigDecimal checkHistoricalBankBalance(DebitCard debitCard, Integer n) {
        if (debitCard == null || n == null) {
            throw new MyException("ARGS ARE NULL");
        }
        return debitCard.checkHistoricalBankBalance(n);
    }

    Integer cancelTransactionsNumber(DebitCard debitCard) {
        if (debitCard == null) {
            throw new MyException("ARGS ARE NULL");
        }
        return debitCard.cancelTransactionsNumber();
    }

    void clearHistoryOfTransactions(DebitCard debitCard) {
        debitCard.clearHistoryOfTransactions();
    }
}
