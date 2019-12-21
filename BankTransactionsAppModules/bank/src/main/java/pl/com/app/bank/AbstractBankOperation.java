package pl.com.app.bank;


import pl.com.app.bank.enums.Operation;
import pl.com.app.exceptions.MyException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBankOperation implements BankOperation {
    private BigDecimal bankBalance;
    private List<String> operations = new ArrayList<>();

    AbstractBankOperation(BigDecimal bankBalance, List<String> operations) {
        setBankBalance(bankBalance);
        this.operations = operations;
    }

    public AbstractBankOperation() {
    }

    public BigDecimal getBankBalance() {
        return bankBalance;
    }

    public List<String> getOperations() {
        return operations;
    }

    public void setOperations(List<String> operations) {
        this.operations = operations;
    }

    public void setBankBalance(BigDecimal bankBalance) {
        if (bankBalance == null || bankBalance.compareTo(BigDecimal.ZERO) < 0){
            throw new MyException("BANK BALANCE EXCEPTION");
        }
        this.bankBalance = bankBalance;
    }

    @Override
    public void payment(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) > 0) {
            bankBalance = bankBalance.add(amount);
            operations.add(String.join("_", Operation.WPLATA.getCode(), String.valueOf(amount)));
        } else {
            operations.add(Operation.ANULOWANA.getCode());
        }
    }

    @Override
    public BigDecimal bankBalance() {
        return bankBalance;
    }

    public BigDecimal checkHistoricalBankBalance(Integer n) {
        if (n == null) {
            throw new MyException("NUMBER IS NULL");
        } else if (n > operations.size()) {
            return BigDecimal.ZERO;
        }

        BigDecimal historicalBankBalance = bankBalance;
        for (int i = operations.size() - 1; i >= operations.size() - n; i--) {
            String oper = operations.get(i);
            if (!oper.equals(Operation.ANULOWANA.getCode()) && !oper.equals(Operation.BLAD_LOGOWANIA.getCode())) {
                String[] operList = oper.split("_");
                if (operList[0].equals(Operation.WPLATA.getCode())) {
                    historicalBankBalance = historicalBankBalance.subtract(new BigDecimal(operList[1]));
                } else {
                    historicalBankBalance = historicalBankBalance.add(new BigDecimal(operList[1]));
                }
            }
        }
        return historicalBankBalance;
    }

    public int cancelTransactionsNumber() {
        return (int) operations
                .stream()
                .filter(Operation.ANULOWANA.getCode()::equals)
                .count();
    }

    public void clearHistoryOfTransactions() {
        operations.clear();
    }

}
