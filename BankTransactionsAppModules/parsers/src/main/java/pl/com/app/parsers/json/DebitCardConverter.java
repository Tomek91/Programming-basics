package pl.com.app.parsers.json;

import pl.com.app.bank.DebitCard;

import java.util.List;

public class DebitCardConverter extends JsonConverter<List<DebitCard>> {
    public DebitCardConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
