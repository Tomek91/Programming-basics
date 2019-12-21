package pl.com.app.models;


import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class CustomerOrder {
    private String name;
    private String surname;
    private int age;
    private BigDecimal cash;
    private List<Product> products;

    public CustomerOrder() {

    }

    public CustomerOrder(String name, String surname, int age, BigDecimal cash, List<Product> products) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.cash = cash;
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerOrder that = (CustomerOrder) o;
        return age == that.age &&
                Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(cash, that.cash) &&
                Objects.equals(products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, age, cash, products);
    }

    @Override
    public String toString() {
        return "CustomerOrder{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", cash=" + cash +
                ", products=" + products +
                '}';
    }
}
