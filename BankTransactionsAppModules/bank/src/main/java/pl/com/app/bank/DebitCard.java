package pl.com.app.bank;

import pl.com.app.bank.enums.Operation;
import pl.com.app.exceptions.MyException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class DebitCard extends AbstractBankOperation {
    private String pin;

    public DebitCard(BigDecimal accountBalance, String pin, List<String> strings) {
        super(accountBalance, strings);
        this.pin = pin;
    }

    public String getPin() {
        return pin;
    }

    public DebitCard() {
        super();
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        return "DebitCard{" +
                "bankBalance=" + getBankBalance() +
                ", operations=" + getOperations() +
                ", pin='" + pin + '\'' +
                '}';
    }

    @Override
    public void payout(BigDecimal amount) {
        System.out.println("Proszę podać pin");
        Scanner sc = new Scanner(System.in);
        String userPin = sc.nextLine();
        if (checkPin(userPin)) {
            if (amount.compareTo(bankBalance()) < 0) {
                setBankBalance(bankBalance().subtract(amount));
                getOperations().add(String.join("_", Operation.WYPLATA.getCode(), String.valueOf(amount)));
            } else {
                getOperations().add(Operation.ANULOWANA.getCode());
                System.out.println("Operacja została anulowana.");
            }
        } else {
            System.out.println("Podany pin jest niepoprawny.");
            getOperations().add(Operation.BLAD_LOGOWANIA.getCode());
        }
    }

    private boolean checkPin(String pin) {
        if (pin == null) {
            throw new MyException("PIN IS NULL");
        }
        return pin.equals(this.pin);
    }

    @Override
    public void clearHistoryOfTransactions() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Proszę podać pin");
        String userPin = sc.nextLine();
        if (checkPin(userPin)) {
            super.clearHistoryOfTransactions();
        } else {
            System.out.println("Podany pin jest niepoprawny.");
            getOperations().add(Operation.BLAD_LOGOWANIA.getCode());
        }
    }
}
