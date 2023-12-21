package com.ryofac.livbook.livbook.Exceptions;

// Exceção que encapsula as exceções relacionadas ao aplicativo em si
public class AppException extends RuntimeException {
    public AppException(String msg){
        super(msg);
    }
    
}
