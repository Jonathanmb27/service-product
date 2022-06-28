package com.nttdata.handler;

import org.springframework.http.HttpStatus;

public class ClientException extends RuntimeException{
    private HttpStatus httpStatus;
    public ClientException(String message){super(message);}

    public ClientException(HttpStatus httpStatus ,String message){
        super(message);
        this.httpStatus=httpStatus;
    }
    public ClientException(HttpStatus httpStatus ,Throwable cause){
        super(cause);
        this.httpStatus=httpStatus;
    }
    public ClientException(String message ,Throwable cause){
        super(message,cause);

    }
    public String errorMessage(){ return httpStatus.value()+" : ".concat(getMessage());}
    public HttpStatus getHttpStatus() {return  httpStatus;}
}
