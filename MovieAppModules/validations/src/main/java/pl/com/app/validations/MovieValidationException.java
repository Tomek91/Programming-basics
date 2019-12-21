package pl.com.app.validations;


public class MovieValidationException extends CustomException {

    public MovieValidationException(String errorMessage) {
        super(errorMessage);
    }


    @Override
    public String getMessage() {
        return "WALIDACJA FILM: " + this.getErrorMessage() + " " + this.getErrorDate();
    }

}
