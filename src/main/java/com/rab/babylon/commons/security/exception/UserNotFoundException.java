package com.rab.babylon.commons.security.exception;

import java.text.MessageFormat;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(){

    }

    public UserNotFoundException(Throwable cause){
        super(cause);
    }

    public UserNotFoundException(String message){
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause){
        super(message, cause);
    }

    public UserNotFoundException(Enum clazz, Object... args){
        super(MessageFormat.format(clazz.toString(), args));
    }

    public UserNotFoundException(Enum clazz, Throwable cause, Object... args){
        super(MessageFormat.format(clazz.toString(), args), cause);
    }
}
