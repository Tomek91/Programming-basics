module models {
    exports pl.com.app.model;
    requires validations;

    opens pl.com.app.model to gson;
}