package pl.com.app.model;


import java.util.Arrays;
import java.util.Objects;

public class CurrencyModel {
    private String table;
    private String no;
    private String effectiveDate;
    private Rate[] rates;

    public CurrencyModel() {
    }

    public CurrencyModel(String table, String no, String effectiveDate, Rate[] rates) {
        this.table = table;
        this.no = no;
        this.effectiveDate = effectiveDate;
        this.rates = rates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyModel that = (CurrencyModel) o;
        return Objects.equals(table, that.table) &&
                Objects.equals(no, that.no) &&
                Objects.equals(effectiveDate, that.effectiveDate) &&
                Arrays.equals(rates, that.rates);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(table, no, effectiveDate);
        result = 31 * result + Arrays.hashCode(rates);
        return result;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Rate[] getRates() {
        return rates;
    }

    public void setRates(Rate[] rates) {
        this.rates = rates;
    }

    @Override
    public String toString() {
        return "CurrencyModel{" +
                "\n table='" + table + '\'' +
                ",\n no='" + no + '\'' +
                ",\n effectiveDate='" + effectiveDate + '\'' +
                ",\n rates=" + (rates == null ? null : Arrays.asList(rates)) + "\n" +
                '}';
    }
}
