package me.splm.app.inject.processor.exception;


public class NotExtendException extends Exception {
    public NotExtendException() {
    }

    public NotExtendException(String message) {
        super(message);
    }

    public NotExtendException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotExtendException(Throwable cause) {
        super(cause);
    }

    public NotExtendException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
