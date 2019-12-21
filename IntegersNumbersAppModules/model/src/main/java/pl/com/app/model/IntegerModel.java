package pl.com.app.model;


import java.util.Objects;

public class IntegerModel {
    private int n1;
    private int n2;
    private int n3;

    public IntegerModel() {
    }

    public IntegerModel(int n1, int n2, int n3) {
        this.n1 = n1;
        this.n2 = n2;
        this.n3 = n3;
    }

    public int getN1() {
        return n1;
    }

    public void setN1(int n1) {
        this.n1 = n1;
    }

    public int getN2() {
        return n2;
    }

    public void setN2(int n2) {
        this.n2 = n2;
    }

    public int getN3() {
        return n3;
    }

    public void setN3(int n3) {
        this.n3 = n3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntegerModel that = (IntegerModel) o;
        return n1 == that.n1 &&
                n2 == that.n2 &&
                n3 == that.n3;
    }

    @Override
    public int hashCode() {
        return Objects.hash(n1, n2, n3);
    }

    @Override
    public String toString() {
        return "IntegerModel{" +
                "n1=" + n1 +
                ", n2=" + n2 +
                ", n3=" + n3 +
                '}';
    }
}
