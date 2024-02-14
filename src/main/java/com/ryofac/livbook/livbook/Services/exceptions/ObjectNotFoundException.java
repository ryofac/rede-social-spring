package com.ryofac.livbook.livbook.Services.exceptions;

import jakarta.persistence.EntityNotFoundException;

/*
 * Encapsula a lógica para uma pesquisa em que a entidade não é encontrada no banco de dados
*/
public class ObjectNotFoundException extends EntityNotFoundException {

    public ObjectNotFoundException(String message){
        super(message);
    }
}
