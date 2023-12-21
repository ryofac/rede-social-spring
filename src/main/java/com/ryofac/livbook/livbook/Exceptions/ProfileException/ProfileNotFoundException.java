package com.ryofac.livbook.livbook.Exceptions.ProfileException;

// Essa exceção é lançada quando algum elemento em sua pesquisa não existe no repository
// A sua mensagem é passada no construtor ao longo do código
public class ProfileNotFoundException extends ProfileException {
    public ProfileNotFoundException(String message) {
        super(message);
    }

}
