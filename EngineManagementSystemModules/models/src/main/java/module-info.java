module models {
    exports pl.com.app.model.enums;
    exports pl.com.app.model;
    requires exceptions;

    opens pl.com.app.model to gson;
}