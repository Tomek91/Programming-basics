package pl.com.app.model;


import pl.com.app.exceptions.MyException;
import pl.com.app.model.enums.CarBodyType;
import pl.com.app.model.enums.Colour;
import pl.com.app.model.enums.Component;

import java.util.List;
import java.util.Objects;

public class CarBody {
    private Colour colour;
    private CarBodyType bType;
    private List<Component> components;

    public CarBody(Colour colour, CarBodyType bType, List<Component> components) {
        this.colour = colour;
        this.bType = bType;
        this.components = components;
    }

    public CarBody() {
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public CarBodyType getbType() {
        return bType;
    }

    public void setbType(CarBodyType bType) {
        this.bType = bType;
    }

    public List<Component> getComponents() {
        return components;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarBody carBody = (CarBody) o;
        return colour == carBody.colour &&
                bType == carBody.bType &&
                Objects.equals(components, carBody.components);
    }

    @Override
    public int hashCode() {
        return Objects.hash(colour, bType, components);
    }

    @Override
    public String toString() {
        return "CarBody{" +
                "colour=" + colour +
                ", bType=" + bType +
                ", components=" + components +
                '}';
    }

    public void setComponents(List<Component> components) {
        try {
            if (components == null || components.stream().anyMatch(x -> !x.toString().matches("[A-Z\\s]+"))) {
                throw new NullPointerException("COMPONENTS IS NULL");
            }
            this.components = components;
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }
    }
}