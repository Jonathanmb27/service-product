package com.nttdata.handler;

import com.nttdata.handler.json.JsonError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(basePackages = {"com.nttdata.controller"})
public class RestExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<JsonError> handleException(ServerHttpRequest request, Exception e){
        String errorUrl=request.getURI().toString();
        if(e instanceof ClientException){
            ClientException clientException=(ClientException) e;
            return new ResponseEntity<>(new JsonError(errorUrl,e.getMessage()),clientException.getHttpStatus());
        }else{
            return new ResponseEntity<>(new JsonError(errorUrl,"Unexpected exception: "+e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
