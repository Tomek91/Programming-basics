package pl.com.app.parsers.data;

import pl.com.app.exceptions.MyException;

import java.time.LocalDate;

public class LocalDateParser implements Parser<LocalDate> {
    @Override
    public LocalDate parse(String line) {
        LocalDate value;
        try {
            value = LocalDate.parse(line);
        } catch (Exception e) {
            throw new MyException("LOCAL DATE PARSE EXCEPTION");
        }
        return value;
    }
}
