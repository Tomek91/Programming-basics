package pl.com.app.model;

import pl.com.app.validations.CustomException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CustomExceptionModel {
    private Map<String, List<String>> exceptions = new HashMap<>();

    public CustomExceptionModel() {
    }

    public Map<String, List<String>> getExceptions() {
        return exceptions;
    }

    public void setExceptions(Map<String, List<String>> exceptions) {
        this.exceptions = exceptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomExceptionModel model = (CustomExceptionModel) o;
        return Objects.equals(exceptions, model.exceptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exceptions);
    }

    @Override
    public String toString() {
        return "CustomExceptionModel{" +
                "exceptions=" + exceptions +
                '}';
    }
}
