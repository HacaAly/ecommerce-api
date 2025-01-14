package com.hikadobushido.ecommerce_java.config.middleware;
import com.hikadobushido.ecommerce_java.common.exception.*;
import com.hikadobushido.ecommerce_java.model.ErrorResponse;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
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

    @ExceptionHandler({BadRequestException.class, InventoryException.class})
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
    public @ResponseBody ErrorResponse handlerGenericException(HttpServletRequest req, HttpServletResponse resp, Exception exception) {
        log.error("Terjadi Error, status error: " + HttpStatus.INTERNAL_SERVER_ERROR + " error message: " + exception.getMessage());
        if (exception instanceof BadCredentialsException ||
                exception instanceof AccountStatusException ||
                exception instanceof AccessDeniedException ||
                exception instanceof SignatureException ||
                exception instanceof ExpiredJwtException ||
                exception instanceof AuthenticationException ||
                exception instanceof InsufficientAuthenticationException
        ) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return ErrorResponse.builder()
                    .errorCode(HttpStatus.FORBIDDEN.value())
                    .message(exception.getMessage())
                    .timeStamp(LocalDateTime.now())
                    .build();
        }
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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

    @ExceptionHandler({
            UsernameAlreadyExistsException.class,
            EmailAlreadyExistsException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody ErrorResponse handleConflictException(HttpServletRequest req, Exception exception) {
        return ErrorResponse.builder()
                .errorCode(HttpStatus.CONFLICT.value())
                .message(exception.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(ForbiddenAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public @ResponseBody ErrorResponse handleForbiddenException(HttpServletRequest req, Exception exception) {
        return ErrorResponse.builder()
                .errorCode(HttpStatus.FORBIDDEN.value())
                .message(exception.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(RequestNotPermitted.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public @ResponseBody ErrorResponse handleRateLimitException(HttpServletRequest req, Exception exception) {
        return ErrorResponse.builder()
                .errorCode(HttpStatus.TOO_MANY_REQUESTS.value())
                .message(exception.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
    }
}
