package pl.com.app.model;

import pl.com.app.exceptions.MyException;
import pl.com.app.model.enums.TyreType;

import java.util.Objects;

public class Wheel {
    private String model;
    private Integer size;
    private TyreType wType;

    public Wheel(String model, Integer size, TyreType wType) {
        setModel(model);
        setSize(size);
        this.wType = wType;
    }

    public Wheel() {
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

    public void setSize(Integer size) {
        try {
            if (size == null || size < 0) {
                throw new NullPointerException("SIZE IS NULL");
            }
            this.size = size;
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }
    }

    public String getModel() {
        return model;
    }

    public Integer getSize() {
        return size;
    }

    public TyreType getwType() {
        return wType;
    }

    public void setwType(TyreType wType) {
        this.wType = wType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wheel wheel = (Wheel) o;
        return Objects.equals(model, wheel.model) &&
                Objects.equals(size, wheel.size) &&
                wType == wheel.wType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, size, wType);
    }

    @Override
    public String toString() {
        return "Wheel{" +
                "model='" + model + '\'' +
                ", size=" + size +
                ", wType=" + wType +
                '}';
    }
}

