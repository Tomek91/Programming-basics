package pl.com.app.model;


import pl.com.app.exceptions.MyException;
import pl.com.app.model.enums.Color;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class Car {
    private String model;
    private BigDecimal price;
    private Color color;
    private Integer mileage;
    private List<String> components;

    public Car(String model, BigDecimal price, Color color, Integer mileage, List<String> components) {
        setModel(model);
        setPrice(price);
        this.color = color;
        setMileage(mileage);
        setComponents(components);
    }

    public Car() {
    }

    public String getModel() {
        return model;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Integer getMileage() {
        return mileage;
    }

    public List<String> getComponents() {
        return components;
    }

    public void setModel(String model) {
        if (model == null || !model.matches("[A-Z\\s]+")) {
            throw new MyException("MODEL ERROR");
        }
        this.model = model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(model, car.model) &&
                Objects.equals(price, car.price) &&
                color == car.color &&
                Objects.equals(mileage, car.mileage) &&
                Objects.equals(components, car.components);
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", price=" + price +
                ", color=" + color +
                ", mileage=" + mileage +
                ", components=" + components +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, price, color, mileage, components);
    }

    public void setPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new MyException("PRICE ERROR");
        }
        this.price = price;
    }

    public void setMileage(Integer mileage) {
        if (mileage == null || mileage < 0) {
            throw new MyException("MILEAGE ERROR");
        }
        this.mileage = mileage;
    }

    public void setComponents(List<String> components) {
        if (components == null || components.stream().anyMatch(x -> !x.matches("[A-Z\\s]+"))) {
            throw new MyException("COMPONENTS ERROR");
        }
        this.components = components;
    }
}
