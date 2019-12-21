package pl.com.app.model;


import java.util.Objects;

public class Customer {
    private String name;
    private String surname;
    private int age;
    private int cash;
    private String preferences;

    public Customer() {
    }

    public Customer(String name, String surname, int age, int cash, String preferences) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.cash = cash;
        this.preferences = preferences;
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

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return age == customer.age &&
                cash == customer.cash &&
                Objects.equals(name, customer.name) &&
                Objects.equals(surname, customer.surname) &&
                Objects.equals(preferences, customer.preferences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, age, cash, preferences);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", cash=" + cash +
                ", preferences='" + preferences + '\'' +
                '}';
    }
}