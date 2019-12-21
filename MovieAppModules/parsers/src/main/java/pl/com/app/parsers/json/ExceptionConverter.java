package pl.com.app.parsers.json;


import pl.com.app.model.CustomExceptionModel;

public class ExceptionConverter extends JsonConverter<CustomExceptionModel> {
    public ExceptionConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
