package pl.com.app.bank.enums;

public enum Operation {
    WPLATA("WPLATA"),
    WYPLATA("WYPLATA"),
    ANULOWANA("ANULOWANA"),
    BLAD_LOGOWANIA("BLAD_LOGOWANIA");

    private final String code;

    Operation(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
