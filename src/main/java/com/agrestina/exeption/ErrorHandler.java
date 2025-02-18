package com.agrestina.exeption;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ErrorHandler {

    private final MessageSource messageSource;

    public ErrorHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler({EntityNotFoundException.class, NoSuchElementException.class})
    public ResponseEntity<String> handleError404() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getMessage("error.notfound"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleError400(MethodArgumentNotValidException ex) {
        var errors = ex.getFieldErrors().stream()
                .map(this::getErrorDataValidation)
                .toList();
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleError400(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(getMessage("error.badrequest"));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleErrorBusinessRule(ValidationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleErrorBadCredentials() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(getMessage("error.unauthorized"));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleErrorAuthentication() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(getMessage("error.authentication"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleErrorAccessDenied() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(getMessage("error.forbidden"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleError500(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(getMessage("error.internal") + ": " + ex.getLocalizedMessage());
    }

    private ErrorDataValidation getErrorDataValidation(FieldError error) {
        String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
        return new ErrorDataValidation(error.getField(), message);
    }

    private String getMessage(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    private record ErrorDataValidation(String field, String message) {
    }
}
