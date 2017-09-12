package com.rab.babylon.commons.security.exception;

import java.text.MessageFormat;

public class ApplicationException extends RuntimeException{

    public ApplicationException(){

    }

    public ApplicationException(Throwable cause){
        super(cause);
    }

    public ApplicationException(String message){
        super(message);
    }

    public ApplicationException(String message, Throwable cause){
        super(message, cause);
    }

    public ApplicationException(Enum clazz, Object... args){
        super(MessageFormat.format(clazz.toString(), args));
    }

    public ApplicationException(Enum clazz, Throwable cause, Object... args){
        super(MessageFormat.format(clazz.toString(), args), cause);
    }
}
