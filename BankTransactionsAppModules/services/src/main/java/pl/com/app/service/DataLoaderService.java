package pl.com.app.service;

import pl.com.app.bank.DebitCard;
import pl.com.app.exceptions.MyException;
import pl.com.app.parsers.json.DebitCardConverter;

import java.util.List;

class DataLoaderService {

    List<DebitCard> loadDebitCards(String fileName) {
        if (fileName == null) {
            throw new MyException("FILE NAME IS NULL");
        }
        DebitCardConverter debitCardConverter = new DebitCardConverter(fileName);
        return debitCardConverter.fromJson().orElseThrow(() -> new MyException("DEBIT CARD CONVERTER EXCEPTION"));
    }
}
