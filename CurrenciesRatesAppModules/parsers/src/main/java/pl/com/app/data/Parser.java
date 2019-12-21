package pl.com.app.data;

@FunctionalInterface
public interface Parser<T> {
    T parse(final String line);
}
