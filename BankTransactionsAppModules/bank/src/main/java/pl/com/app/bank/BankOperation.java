package pl.com.app.bank;

import java.math.BigDecimal;

public interface BankOperation {
    void payment(BigDecimal amount);
    void payout(BigDecimal amount);
    BigDecimal bankBalance();
}
