package pl.com.app.parsers.data;

@FunctionalInterface
public interface Parser<T> {
    T parse(final String line);
}
