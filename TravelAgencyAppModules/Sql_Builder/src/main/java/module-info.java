module Sql.Builder {
    requires java.sql;
    exports pl.com.app.sqlbuilder.creator to connection, repository;
    exports pl.com.app.sqlbuilder.types to connection, repository;
}