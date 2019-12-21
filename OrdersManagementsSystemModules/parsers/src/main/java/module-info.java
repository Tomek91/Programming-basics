module parsers {
    requires exceptions;
    requires models;
    requires gson;
    requires java.sql;

    exports pl.com.app.parsers.data to readers, services;
    exports pl.com.app.parsers.json to services, email;
}