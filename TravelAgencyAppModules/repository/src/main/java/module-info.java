module repository {
    exports pl.com.app.repository to services;

    requires java.sql;
    requires models;
    requires exceptions;
    requires Sql.Builder;
    requires connection;
}