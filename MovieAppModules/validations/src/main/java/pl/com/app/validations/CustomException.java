package pl.com.app.validations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomException extends RuntimeException {
    private String errorMessage;
    private LocalDateTime errorDate;

    public CustomException() {
    }


    private static Map<String, List<CustomException>> exceptionMap = new HashMap<>();

    public static Map<String, List<CustomException>> getExceptionMap() {
        return exceptionMap;
    }

    public static void setExceptionMap(Map<String, List<CustomException>> exceptionMap) {
        CustomException.exceptionMap = exceptionMap;
    }

    public static void addPairToExceptionMap(String key, CustomException e) {
        List<CustomException> exceptionList;
        if (getExceptionMap().containsKey(key)) {
            exceptionList = getExceptionMap().get(key);
        } else {
            exceptionList = new ArrayList<>();
        }
        exceptionList.add(e);
        getExceptionMap().put(key, exceptionList);
    }

    public static void showExceptionMap() {
        if (getExceptionMap().size() == 0) {
            System.out.println("BRAK DANYCH");
            return;
        }
        getExceptionMap().forEach((k, v) -> {
            System.err.println(k);
            v.forEach(System.err::println);
        });
    }

    public CustomException(String errorMessage) {
        this.errorMessage = errorMessage;
        this.errorDate = LocalDateTime.now();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public LocalDateTime getErrorDate() {
        return errorDate;
    }

    public void setErrorDate(LocalDateTime errorDate) {
        this.errorDate = errorDate;
    }

    @Override
    public String getMessage() {
        return "CUSTOM: " + this.getErrorMessage() + " " + this.getErrorDate();
    }
}
