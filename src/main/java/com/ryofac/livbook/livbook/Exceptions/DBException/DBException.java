package com.ryofac.livbook.livbook.Exceptions.DBException;

// Esse erro é lançado em caso de erro com o banco de dados
public class DBException extends RuntimeException {
    public DBException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
