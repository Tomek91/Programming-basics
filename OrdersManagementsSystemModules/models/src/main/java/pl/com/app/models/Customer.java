package pl.com.app.models;

import pl.com.app.exceptions.MyException;

import java.util.Objects;


public class Customer {
    private String name;
    private String surname;
    private Integer age;
    private String email;

    public Customer() {
    }

    public Customer(String name, String surname, Integer age, String email) {
        setName(name);
        setSurname(surname);
        setAge(age);
        setEmail(email);
    }

    public void setName(String name) {
        if (name == null || !name.matches("[A-Z ]+")) {
            throw new MyException("CUSTOMER SET NAME ERROR");
        }
        this.name = name;
    }

    public void setSurname(String surname) {
        if (name == null || !name.matches("[A-Z ]+")) {
            throw new MyException("CUSTOMER SET SURNAME ERROR");
        }
        this.surname = surname;
    }

    public void setAge(Integer age) {
        if (age == null || age < 18) {
            throw new MyException("CUSTOMER SET AGE ERROR");
        }
        this.age = age;
    }

    public void setEmail(String email) {
        if (email == null || !email.matches("[a-z0-9]+@[a-z]+.(pl|com)")) {
            throw new MyException("CUSTOMER SET EMAIL ERROR");
        }
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(name, customer.name) &&
                Objects.equals(surname, customer.surname) &&
                Objects.equals(age, customer.age) &&
                Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, age, email);
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Integer getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}
