module parsers {
    exports pl.com.app.parsers.data to readers;
    exports pl.com.app.parsers.json to services;
    requires gson;
    requires java.sql;
    requires exceptions;
    requires models;
}