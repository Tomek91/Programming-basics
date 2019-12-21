module parsers {
    exports pl.com.app.parsers.properties;
    exports pl.com.app.parsers.movies;
    exports pl.com.app.parsers.json;
    requires validations;
    requires gson;
    requires java.sql;
    requires models;

    opens pl.com.app.parsers.movies to gson;
}