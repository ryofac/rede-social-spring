package com.ryofac.livbook.livbook.Exceptions.PostException;

// Essa exceção é lançada quando algum elemento em sua tentativa de criação já existe nos repositories
// A sua mensagem é passada no construtor ao longo do código
public class PostAlreadyExistsException extends PostException {

    public PostAlreadyExistsException(String message) {
        super(message);
        
    }
}
