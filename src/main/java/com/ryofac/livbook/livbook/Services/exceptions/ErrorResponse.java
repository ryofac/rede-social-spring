package com.ryofac.livbook.livbook.Services.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/* Essa class é a classe de retorno do erro por meio da response, que possui a timestamp, o status code e mais informações */

@Data
@RequiredArgsConstructor
public class ErrorResponse {
    private final int status;
    private final String message;
    private long timestamp;

    /* O spring ainda contém o trace, porém não é bom retorná-lo na fase de finalização do projeto,
    pode levar a ataques de injeção de código */
    private String stackTrace;

    // Classe estática que se inicia junto com o error response
    private List<ValidationError> errors;

    @Data
    @RequiredArgsConstructor
    // Encapsula o erro de validação
    private static class ValidationError {
        private final String field;
        private final String message;
    }


    // Classe para adcionar um erro de validação na resposta
    public void addValidationError(String field, String message) {
        if(Objects.isNull(errors))
            // Se não existir nenhum erro, cria-se uma lista de erros
            this.errors = new ArrayList<>();
        this.errors.add(new ValidationError(field, message));
    }
}
