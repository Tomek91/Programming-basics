package pl.com.app.model;


import pl.com.app.exceptions.MyException;
import pl.com.app.model.enums.EngineType;

import java.util.Objects;

public class Engine {
    private EngineType eType;
    private Double power;

    public Engine(EngineType eType, Double power) {
        this.eType = eType;
        setPower(power);
    }

    public Engine() {
    }

    public EngineType geteType() {
        return eType;
    }

    public void seteType(EngineType eType) {
        this.eType = eType;
    }

    public Double getPower() {
        return power;
    }

    public void setPower(Double power) {
        try {
            if (power == null || power < 0) {
                throw new NullPointerException("POWER IS NULL");
            }
            this.power = power;
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Engine engine = (Engine) o;
        return eType == engine.eType &&
                Objects.equals(power, engine.power);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eType, power);
    }

    @Override
    public String toString() {
        return "Engine{" +
                "eType=" + eType +
                ", power=" + power +
                '}';
    }
}
