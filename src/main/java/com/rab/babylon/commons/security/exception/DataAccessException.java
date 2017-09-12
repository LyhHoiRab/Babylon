package com.rab.babylon.commons.security.exception;

import java.text.MessageFormat;

public class DataAccessException extends RuntimeException{

    public DataAccessException(){

    }

    public DataAccessException(Throwable cause){
        super(cause);
    }

    public DataAccessException(String message){
        super(message);
    }

    public DataAccessException(String message, Throwable cause){
        super(message, cause);
    }

    public DataAccessException(Enum clazz, Object... args){
        super(MessageFormat.format(clazz.toString(), args));
    }

    public DataAccessException(Enum clazz, Throwable cause, Object... args){
        super(MessageFormat.format(clazz.toString(), args), cause);
    }
}
