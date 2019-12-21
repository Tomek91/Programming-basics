package pl.com.app.model;


import pl.com.app.exceptions.MyException;

import java.math.BigDecimal;
import java.util.Objects;


public class Car {
    private String model;
    private BigDecimal price;
    private Integer mileage;
    private Engine engine;
    private CarBody carBody;
    private Wheel wheel;

    public Car(Car car) {
        setModel(car.getModel());
        setPrice(car.getPrice());
        setMileage(car.getMileage());
        setEngine(car.getEngine());
        setCarBody(car.getCarBody());
        setWheel(car.getWheel());
    }

    public Car() {
    }

    public String getModel() {
        return model;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getMileage() {
        return mileage;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public CarBody getCarBody() {
        return carBody;
    }

    public void setCarBody(CarBody carBody) {
        this.carBody = carBody;
    }

    public Wheel getWheel() {
        return wheel;
    }

    public void setWheel(Wheel wheel) {
        this.wheel = wheel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(model, car.model) &&
                Objects.equals(price, car.price) &&
                Objects.equals(mileage, car.mileage) &&
                Objects.equals(engine, car.engine) &&
                Objects.equals(carBody, car.carBody) &&
                Objects.equals(wheel, car.wheel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, price, mileage, engine, carBody, wheel);
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", price=" + price +
                ", mileage=" + mileage +
                ", engine=" + engine +
                ", carBody=" + carBody +
                ", wheel=" + wheel +
                '}';
    }

    public void setModel(String model) {
        try {
            if (model == null || !model.matches("[A-Z\\s]+")) {
                throw new NullPointerException("MODEL IS NULL");
            }
            this.model = model;
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }
    }

    public void setMileage(Integer mileage) {
        try {
            if (mileage == null || mileage < 0) {
                throw new NullPointerException("MILEAGE IS NULL");
            }
            this.mileage = mileage;
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }
    }

    public void setPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new MyException("PRICE ERROR");
        }
        this.price = price;
    }
}

