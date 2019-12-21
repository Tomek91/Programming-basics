module models {
    exports pl.com.app.model to parsers, services;
    exports pl.com.app.model.enums to parsers, readers, services;

    requires exceptions;

    opens pl.com.app.model to gson;
}