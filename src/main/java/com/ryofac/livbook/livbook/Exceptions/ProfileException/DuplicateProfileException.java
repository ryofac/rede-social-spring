package com.ryofac.livbook.livbook.Exceptions.ProfileException;

// Exceção lançada ao encontrar um post duplicado no banco de dados
public class DuplicateProfileException extends ProfileException {
    public DuplicateProfileException(String message) {
        super(message);
        
    }
    
}
