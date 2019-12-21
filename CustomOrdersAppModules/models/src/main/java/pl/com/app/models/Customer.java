package pl.com.app.models;


import java.math.BigDecimal;
import java.util.Objects;

public class Customer {
    private String name;
    private String surname;
    private int age;
    private BigDecimal cash;

    public Customer() {
    }

    public Customer(String name, String surname, int age, BigDecimal cash) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.cash = cash;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return age == customer.age &&
                Objects.equals(name, customer.name) &&
                Objects.equals(surname, customer.surname) &&
                Objects.equals(cash, customer.cash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, age, cash);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", cash=" + cash +
                '}';
    }


    public static CustomerBuilder builder() {
        return new CustomerBuilder();
    }

    public static final class CustomerBuilder {
        private String name;
        private String surname;
        private int age;
        private BigDecimal cash;

        public CustomerBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CustomerBuilder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public CustomerBuilder age(int age) {
            this.age = age;
            return this;
        }

        public CustomerBuilder cash(BigDecimal cash) {
            this.cash = cash;
            return this;
        }

        public Customer build() {
            Customer customer = new Customer();
            customer.setName(name);
            customer.setSurname(surname);
            customer.setAge(age);
            customer.setCash(cash);
            return customer;
        }
    }
}
