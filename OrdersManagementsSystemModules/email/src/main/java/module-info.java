module email {
    requires exceptions;
    requires parsers;
    requires mail;

    exports pl.com.app.email to services;
}