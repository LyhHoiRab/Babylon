package com.rab.babylon.commons.security.exception;

import java.text.MessageFormat;

public class BrowserNotSupportException extends RuntimeException{

    public BrowserNotSupportException(){

    }

    public BrowserNotSupportException(Throwable cause){
        super(cause);
    }

    public BrowserNotSupportException(String message){
        super(message);
    }

    public BrowserNotSupportException(String message, Throwable cause){
        super(message, cause);
    }

    public BrowserNotSupportException(Enum clazz, Object... args){
        super(MessageFormat.format(clazz.toString(), args));
    }

    public BrowserNotSupportException(Enum clazz, Throwable cause, Object... args){
        super(MessageFormat.format(clazz.toString(), args), cause);
    }
}
