module models {

    exports pl.com.app.models to parsers, services, repository;

    opens pl.com.app.models to gson;
}