module models {
    requires exceptions;

    exports pl.com.app.models to parsers, services;
    exports pl.com.app.models.enums to parsers, readers, services;

    opens pl.com.app.models to gson;
}