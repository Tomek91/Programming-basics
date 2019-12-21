package pl.com.app.exceptions;


import java.time.LocalDateTime;


public class MyException extends RuntimeException {
    private String exceptionMessage;
    private LocalDateTime exceptionDateTime;

    public MyException(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
        this.exceptionDateTime = LocalDateTime.now();
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public LocalDateTime getExceptionDateTime() {
        return exceptionDateTime;
    }

    public void setExceptionDateTime(LocalDateTime exceptionDateTime) {
        this.exceptionDateTime = exceptionDateTime;
    }

    @Override
    public String toString() {
        return "MyException{" +
                "exceptionMessage='" + exceptionMessage + '\'' +
                ", exceptionDateTime=" + exceptionDateTime +
                '}';
    }
}

