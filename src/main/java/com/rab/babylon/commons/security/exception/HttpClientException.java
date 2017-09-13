package com.rab.babylon.commons.security.exception;

import java.text.MessageFormat;

public class HttpClientException extends RuntimeException{

    public HttpClientException(){

    }

    public HttpClientException(Throwable cause){
        super(cause);
    }

    public HttpClientException(String message){
        super(message);
    }

    public HttpClientException(String message, Throwable cause){
        super(message, cause);
    }

    public HttpClientException(Enum clazz, Object... args){
        super(MessageFormat.format(clazz.toString(), args));
    }

    public HttpClientException(Enum clazz, Throwable cause, Object... args){
        super(MessageFormat.format(clazz.toString(), args), cause);
    }
}
