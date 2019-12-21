package pl.com.app.validations;


public class TimeValidationException extends CustomException {
    public TimeValidationException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String getMessage() {
        return "WALIDACJA CZAS: " + this.getErrorMessage() + " " + this.getErrorDate();
    }
}
