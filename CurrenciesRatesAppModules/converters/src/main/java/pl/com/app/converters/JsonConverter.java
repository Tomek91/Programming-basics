package pl.com.app.converters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pl.com.app.exceptions.MyException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

public abstract class JsonConverter<T> {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
    private final Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    public Optional<T> fromJson(String jsonData) {
        try {
            if (jsonData == null) {
                throw new NullPointerException("DATA IS NULL");
            }
            return Optional.of(gson.fromJson(jsonData, type));
        } catch (Exception e) {
            throw new MyException("JSON PARSER EXCEPTION");
        }
    }

    public String toJson(final T element) {
        try {
            if (element == null) {
                throw new NullPointerException("ELEMENT IS NULL");
            }
            return gson.toJson(element, type);
        } catch (Exception e) {
            throw new MyException("JSON PARSER EXCEPTION");
        }
    }
}
