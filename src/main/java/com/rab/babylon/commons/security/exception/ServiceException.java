package com.rab.babylon.commons.security.exception;

import java.text.MessageFormat;

public class ServiceException extends RuntimeException{

    public ServiceException(){

    }

    public ServiceException(Throwable cause){
        super(cause);
    }

    public ServiceException(String message){
        super(message);
    }

    public ServiceException(String message, Throwable cause){
        super(message, cause);
    }

    public ServiceException(Enum clazz, Object... args){
        super(MessageFormat.format(clazz.toString(), args));
    }

    public ServiceException(Enum clazz, Throwable cause, Object... args){
        super(MessageFormat.format(clazz.toString(), args), cause);
    }
}
