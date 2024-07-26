package com.cordigocerto.trilhabackend.controllers.exceptions;


import com.cordigocerto.trilhabackend.services.exceptions.EmptyCredentialsException;
import com.cordigocerto.trilhabackend.services.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ProblemDetail pb = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pb.setDetail(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pb);
    }

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(
            NoResourceFoundException ex,
            HttpHeaders headers, HttpStatusCode status,
            WebRequest request) {
        ProblemDetail pb = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pb.setDetail("exceçao 2 dizendo que usuario nao existe");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pb);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers, HttpStatusCode status,
            WebRequest request) {
        ProblemDetail pb = ProblemDetail.forStatus(HttpStatus.METHOD_NOT_ALLOWED);
        pb.setDetail("O método HTTP selecionado não é suportado pelo endpoint!");
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(pb);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        ProblemDetail pb = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        pb.setDetail("Houve um conflito ao realizar essa operação");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(pb);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        ProblemDetail pb = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pb.setDetail("Não foi possivel atualizar, pois o valor informado não foi um 'id'!");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pb);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        ProblemDetail pb = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pb.setDetail("O servidor não pode processar a solicitação porque o corpo da solicitação é inválido ou malformado.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pb);

    }

    @ExceptionHandler(EmptyCredentialsException.class)
    protected ResponseEntity<Object> handleEmptyCredentialsException(EmptyCredentialsException ex) {
        ProblemDetail pb = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pb.setDetail("Não foi possivel atualizar! Alguma credencial pode estar vazia.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pb);
    }



}
