package pl.com.app.parsers.data;

import pl.com.app.exceptions.MyException;
import pl.com.app.models.enums.Category;

public class CategoryParser implements Parser<Category> {
    @Override
    public Category parse(String line) {
        Category value;
        try {
            value = Category.valueOf(line);
        } catch (Exception e) {
            throw new MyException("CATEGORY PARSE EXCEPTION");
        }
        return value;
    }
}
