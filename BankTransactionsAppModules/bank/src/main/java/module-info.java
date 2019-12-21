module bank {
    exports pl.com.app.bank;
    exports pl.com.app.bank.enums;
    requires exceptions;

    opens pl.com.app.bank to gson;
}