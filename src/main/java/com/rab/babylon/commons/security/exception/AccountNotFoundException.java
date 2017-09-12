package com.rab.babylon.commons.security.exception;

import java.text.MessageFormat;

public class AccountNotFoundException extends RuntimeException{

    public AccountNotFoundException(){

    }

    public AccountNotFoundException(Throwable cause){
        super(cause);
    }

    public AccountNotFoundException(String message){
        super(message);
    }

    public AccountNotFoundException(String message, Throwable cause){
        super(message, cause);
    }

    public AccountNotFoundException(Enum clazz, Object... args){
        super(MessageFormat.format(clazz.toString(), args));
    }

    public AccountNotFoundException(Enum clazz, Throwable cause, Object... args){
        super(MessageFormat.format(clazz.toString(), args), cause);
    }
}
