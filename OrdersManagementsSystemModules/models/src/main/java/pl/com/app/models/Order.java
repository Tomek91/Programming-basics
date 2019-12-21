package pl.com.app.models;

import pl.com.app.exceptions.MyException;

import java.time.LocalDate;
import java.util.Objects;


public class Order {
    Customer customer;
    Product product;
    Integer quantity;
    LocalDate orderDate;

    public Order() {
    }

    public Order(Customer customer, Product product, Integer quantity, LocalDate orderDate) {
        setCustomer(customer);
        setProduct(product);
        setQuantity(quantity);
        setOrderDate(orderDate);
    }

    public void setCustomer(Customer customer) {
        if (customer == null) {
            throw new MyException("ORDER SET CUSTOMER ERROR");
        }
        this.customer = customer;
    }

    public void setProduct(Product product) {
        if (product == null) {
            throw new MyException("ORDER SET PRODUCT ERROR");
        }
        this.product = product;
    }

    public void setQuantity(Integer quantity) {
        if (quantity == null || quantity < 0) {
            throw new MyException("ORDER SET QUANTITY ERROR");
        }
        this.quantity = quantity;
    }

    public void setOrderDate(LocalDate orderDate) {
        if (orderDate == null || orderDate.isBefore(LocalDate.now())) {
            throw new MyException("ORDER SET ORDER DATE ERROR");
        }
        this.orderDate = orderDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "customer=" + customer +
                ", product=" + product +
                ", quantity=" + quantity +
                ", orderDate=" + orderDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(customer, order.customer) &&
                Objects.equals(product, order.product) &&
                Objects.equals(quantity, order.quantity) &&
                Objects.equals(orderDate, order.orderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, product, quantity, orderDate);
    }
}
