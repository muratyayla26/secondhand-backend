package com.yayla.secondhand.secondhandbackend.exception;

import com.yayla.secondhand.secondhandbackend.model.response.BaseResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ExceptionHelper exceptionHelper;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> handleUnknownException(Exception exception) {
        return exceptionHelper.getUnknownExceptionResponse(exception);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BaseResponse> handleNotFoundException(NotFoundException exception) {
        log.error(exception.getMessage(), exception);
        try {
            BaseResponse baseResponse = exceptionHelper.createErrorResponse(exception.getMessage());
            return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return exceptionHelper.getUnknownExceptionResponse(ex);
        }
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BaseResponse> handleBusinessException(BusinessException exception) {
        log.error(exception.getMessage(), exception);
        try {
            BaseResponse baseResponse = exceptionHelper.createErrorResponse(exception.getMessage());
            return new ResponseEntity<>(baseResponse, HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception ex) {
            return exceptionHelper.getUnknownExceptionResponse(ex);
        }
    }

    @ExceptionHandler(TokenRefreshException.class)
    public ResponseEntity<BaseResponse> handleBusinessException(TokenRefreshException exception) {
        log.error(exception.getMessage(), exception);
        try {
            BaseResponse baseResponse = exceptionHelper.createErrorResponse(exception.getMessage());
            return new ResponseEntity<>(baseResponse, HttpStatus.FORBIDDEN);
        } catch (Exception ex) {
            return exceptionHelper.getUnknownExceptionResponse(ex);
        }
    }

    @ExceptionHandler({BadCredentialsException.class, MalformedJwtException.class,
            ExpiredJwtException.class, UnsupportedJwtException.class, IllegalArgumentException.class})
    public ResponseEntity<BaseResponse> handleBadCredentialsException(Exception exception) {
        log.error(exception.getMessage(), exception);
        try {
            BaseResponse baseResponse = exceptionHelper.createErrorResponse(exception.getMessage());
            return new ResponseEntity<>(baseResponse, HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            return exceptionHelper.getUnknownExceptionResponse(ex);
        }
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<BaseResponse> handleAccessDeniedException(AccessDeniedException exception) {
        log.error(exception.getMessage(), exception);
        try {
            BaseResponse baseResponse = exceptionHelper.createErrorResponse(exception.getMessage());
            return new ResponseEntity<>(baseResponse, HttpStatus.FORBIDDEN);
        } catch (Exception ex) {
            return exceptionHelper.getUnknownExceptionResponse(ex);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error(exception.getMessage(), exception);
        try {
            Map<String, String> validationErrors = new HashMap<>();
            List<ObjectError> validationErrorList = exception.getBindingResult().getAllErrors();

            validationErrorList.forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String validationMessage = error.getDefaultMessage();
                validationErrors.put(fieldName, validationMessage);
            });

            BaseResponse baseResponse = exceptionHelper.createErrorResponse(validationErrors.toString());
            return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return exceptionHelper.getUnknownExceptionResponse(ex);
        }
    }

}
