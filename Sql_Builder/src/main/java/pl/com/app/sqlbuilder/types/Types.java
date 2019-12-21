package pl.com.app.sqlbuilder.types;

public enum Types {
    INTEGER("integer"),
    VARCHAR("varchar"),
    BOOLEAN("boolean"),
    DOUBLE("double"),
    DATE("date"),
    TIMESTAMP("timestamp");

    private final String code;

    Types(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
