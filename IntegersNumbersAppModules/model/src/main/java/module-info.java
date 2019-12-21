module model {
    exports pl.com.app.model to parsers, services;

    opens pl.com.app.model to gson;
}