package com.hikadobushido.ecommerce_java.config.middleware;
import com.hikadobushido.ecommerce_java.common.errors.*;
import com.hikadobushido.ecommerce_java.model.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GenericExceptionHandler {

    @ExceptionHandler({
            ResourceNotFoundException.class,
            UserNotFoundException.class,
            RoleNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handlerResourceNotFoundException(HttpServletRequest req, ResourceNotFoundException exception) {
        return ErrorResponse.builder()
                .errorCode(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handlerBadRequestException(HttpServletRequest req, BadRequestException exception) {
        return ErrorResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorResponse handlerGenericException(HttpServletRequest req, Exception exception) {
        log.error("Terjadi Error, status error: " + HttpStatus.INTERNAL_SERVER_ERROR + " error message: " + exception.getMessage());
        return ErrorResponse.builder()
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(exception.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleValidationException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(objectError -> {
            String fieldName = ((FieldError)objectError).getField();
            String errorMessage = objectError.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ErrorResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .message(errors.toString())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(InvalidPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public @ResponseBody ErrorResponse handleUnauthorizedException(HttpServletRequest req, Exception exception) {
        return ErrorResponse.builder()
                .errorCode(HttpStatus.UNAUTHORIZED.value())
                .message(exception.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    public @ResponseBody ErrorResponse handleConflictException(HttpServletRequest req, Exception exception) {
        return ErrorResponse.builder()
                .errorCode(HttpStatus.CONFLICT.value())
                .message(exception.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
    }

}
