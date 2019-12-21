package pl.com.app.models;


import pl.com.app.exceptions.MyException;
import pl.com.app.models.enums.Category;

import java.math.BigDecimal;
import java.util.Objects;


public class Product {
    private String name;
    private BigDecimal price;
    Category category;

    public Product() {
    }

    public Product(String name, BigDecimal price, Category category) {
        setName(name);
        setPrice(price);
        setCategory(category);
    }

    public void setName(String name) {
        if (name == null || !name.matches("[A-Z ]+")) {
            throw new MyException("PRODUCT SET NAME EXCEPTION");
        }
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        if (price == null || price.compareTo(new BigDecimal(0.0)) < 0.0) {
            throw new MyException("PRODUCT SET PRICE EXCEPTION");
        }
        this.price = price;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) &&
                Objects.equals(price, product.price) &&
                category == product.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, category);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", category=" + category +
                '}';
    }
}
