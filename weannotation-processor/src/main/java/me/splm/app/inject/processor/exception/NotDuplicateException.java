package me.splm.app.inject.processor.exception;


public class NotDuplicateException extends Exception{
    public NotDuplicateException() {
    }

    public NotDuplicateException(String s) {
        super(s);
    }

    public NotDuplicateException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public NotDuplicateException(Throwable throwable) {
        super(throwable);
    }

    public NotDuplicateException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
