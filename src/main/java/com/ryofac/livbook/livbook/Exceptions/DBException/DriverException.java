package com.ryofac.livbook.livbook.Exceptions.DBException;

// Essa exceção é lançada quando ocorre algum erro relacionado ao driver do banco de dados
public class DriverException extends DBException {
    public DriverException(String message, Throwable e) {
        super(message, e);
    }
    
}
