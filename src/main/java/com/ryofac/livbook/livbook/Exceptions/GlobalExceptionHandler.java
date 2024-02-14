package com.ryofac.livbook.livbook.Exceptions;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ryofac.livbook.livbook.Services.exceptions.ObjectNotFoundException;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

/*
 * Essa é a classe que definitivamente "filtra" as Exceções, toda exceção passa por aqui
*/


// TODO: Transformar os métodos buildErrorResponse em um Builder de ErrorResponse pelo Lombok

 
// Logger do Lombok, serve para "printar" informações da classe no terminal
@Slf4j(topic = "EXCEPTION_HANDLER")
@RestControllerAdvice
// Essa classe estende a RsponseEntityExceptionHandler, que vem do Spring
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    /* Isso aqui é controlado dentro do application.properties 
     * Ajuda a indicar para o spring que pode ou não levar o StackTrace na response do erro
    */
    @Value("${server.error.include-exception}")
    private boolean canPrintStackTrace;


    private ResponseEntity<Object> buildErrorResponse(Exception exception, String message, HttpStatus httpStatus, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), message);
        if(this.canPrintStackTrace) {
            errorResponse.setStackTrace(ExceptionUtils.getStackTrace(exception));
        }

        return ResponseEntity.status(httpStatus).body(errorResponse);


    }
   
    /* Sobreescrevendo o método que trata as exceções de argumentos inválidos*/
    @Override
    @Nullable
    @SuppressWarnings("null") // TODO: fica reclamando de nulável
    protected ResponseEntity<Object> handleMethodArgumentNotValid (
            MethodArgumentNotValidException ex,
            HttpHeaders headers, 
            HttpStatusCode status, 
            WebRequest request) {

            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value() , "Validation error, check field 'errors' for details.");
            for(FieldError fieldError : ex.getBindingResult().getFieldErrors()){
                errorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.unprocessableEntity().body(errorResponse);
    }
    

    /* Lida com erros quaisquer, que não são validados por outros métodos */
    @ExceptionHandler(Exception.class) 
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtException(Exception ex, WebRequest request) {
        final String message = "A unknown error ocurred.";

        // Aciona o logger do Lombok, isto é, manda uma mensagem de erro no terminal
        log.error(message, ex);

        return buildErrorResponse(ex, message, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    /* Lida com erros relacionados a conflitos de estados (Entidades já existentes no banco de dados) */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        String message = ex.getMostSpecificCause().getMessage();
        log.error("Failed to save entity due to integrity problems:" + message, ex);
        return buildErrorResponse(ex, message, HttpStatus.CONFLICT, request);
    }

    /* Lida com erros relacionados a violações de constraints  */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        log.error("Failed to validate element ", ex);
        return buildErrorResponse(ex, ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleObjectNotFoundException(ObjectNotFoundException ex, WebRequest request){
        log.error("Entity not found", ex);
        return buildErrorResponse(ex, ex.getMessage(), HttpStatus.NOT_FOUND, request);
    }


}
