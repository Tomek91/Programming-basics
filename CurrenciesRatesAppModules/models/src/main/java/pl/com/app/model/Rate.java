package pl.com.app.model;



import java.math.BigDecimal;
import java.util.Objects;


public class Rate {
    private String currency;
    private String code;
    private BigDecimal mid;

    public Rate() {
    }

    public Rate(String currency, String code, BigDecimal mid) {
        this.currency = currency;
        this.code = code;
        this.mid = mid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rate rate = (Rate) o;
        return Objects.equals(currency, rate.currency) &&
                Objects.equals(code, rate.code) &&
                Objects.equals(mid, rate.mid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, code, mid);
    }

    @Override
    public String toString() {
        return "\n  Rate{" +
                "\n   currency='" + currency + '\'' +
                ",\n   code='" + code + '\'' +
                ",\n   mid=" + mid + "\n  " +
                '}';
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getMid() {
        return mid;
    }

    public void setMid(BigDecimal mid) {
        this.mid = mid;
    }
}
