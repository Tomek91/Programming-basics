package pl.com.app.json;

import pl.com.app.model.Preference;

import java.util.List;

public class PreferencesConverter extends JsonConverter<List<Preference>> {
    public PreferencesConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
