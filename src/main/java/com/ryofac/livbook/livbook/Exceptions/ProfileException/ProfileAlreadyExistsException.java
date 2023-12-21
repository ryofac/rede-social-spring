package com.ryofac.livbook.livbook.Exceptions.ProfileException;

// Essa exceção é lançada quando algum elemento em sua tentativa de criação já existe nos repositories
// A sua mensagem é passada no construtor ao longo do código
public class ProfileAlreadyExistsException extends ProfileException {

    public ProfileAlreadyExistsException(String message) {
        super(message);
        
    }
}
