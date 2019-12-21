package pl.com.app.service;


import pl.com.app.converters.CurrencyModelConverter;
import pl.com.app.exceptions.MyException;
import pl.com.app.model.CurrencyModel;
import pl.com.app.request.GetRequest;

public class LoadCurrencyService {

    private static final String NBP_URL = "http://api.nbp.pl/api/exchangerates/tables/A/__DATE__/";

    public static CurrencyModel generateCurrencyModel(String date) {
        if (date == null) {
            throw new NullPointerException("DATE IS NULL");
        }
        CurrencyModelConverter currencyModelConverter = new CurrencyModelConverter();
        return currencyModelConverter
                .fromJson(LoadCurrencyService.createGetRequest(date))
                .orElseThrow(() -> new MyException("CURRENCY MODEL EXCEPTION"));
    }

    public static String createGetRequest(String date) {
            if (date == null) {
                throw new MyException("DATE IS NULL");
            }
            String jsonInString = GetRequest.sendGetRequest(NBP_URL.replace("__DATE__", date));
            return jsonInString.substring(1, jsonInString.length() - 1);
    }
}
