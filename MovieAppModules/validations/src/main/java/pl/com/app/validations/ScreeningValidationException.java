package pl.com.app.validations;


public class ScreeningValidationException extends CustomException {
    public ScreeningValidationException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String getMessage() {
        return "WALIDACJA SEANS: " + this.getErrorMessage() + " " + this.getErrorDate();
    }
}
