package org.nostalgia.common.util.exception.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.nostalgia.common.model.response.NostalgiaErrorResponse;
import org.nostalgia.common.util.exception.*;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.sql.SQLException;

/**
 * Global exception handler acting as controller advice for certain use cases happened in the controller.
 */
@Slf4j
@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    NostalgiaErrorResponse handleJsonParseErrors(final HttpMessageNotReadableException exception) {
        log.error(exception.getMessage(), exception);

        if (exception.getCause() instanceof InvalidFormatException invalidFormatException) {
            return NostalgiaErrorResponse.subErrors(invalidFormatException)
                    .header(NostalgiaErrorResponse.Header.VALIDATION_ERROR.getName())
                    .build();
        }

        return NostalgiaErrorResponse.builder()
                .header(NostalgiaErrorResponse.Header.VALIDATION_ERROR.getName())
                .build();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    NostalgiaErrorResponse handleValidationErrors(final MethodArgumentTypeMismatchException exception) {

        log.error(exception.getMessage(), exception);

        return NostalgiaErrorResponse.subErrors(exception)
                .header(NostalgiaErrorResponse.Header.VALIDATION_ERROR.getName())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    NostalgiaErrorResponse handleValidationErrors(final MethodArgumentNotValidException exception) {

        log.error(exception.getMessage(), exception);

        return NostalgiaErrorResponse.subErrors(exception.getBindingResult().getFieldErrors())
                .header(NostalgiaErrorResponse.Header.VALIDATION_ERROR.getName())
                .build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    NostalgiaErrorResponse handlePathVariableErrors(final ConstraintViolationException exception) {
        log.error(exception.getMessage(), exception);

        return NostalgiaErrorResponse.subErrors(exception.getConstraintViolations())
                .header(NostalgiaErrorResponse.Header.VALIDATION_ERROR.getName())
                .build();
    }

    @ExceptionHandler(NostalgiaNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    NostalgiaErrorResponse handleNotExistError(final NostalgiaNotExistException exception) {
        log.error(exception.getMessage(), exception);

        return NostalgiaErrorResponse.builder()
                .header(NostalgiaErrorResponse.Header.NOT_FOUND.getName())
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(NostalgiaBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    NostalgiaErrorResponse handleBadRequestError(final NostalgiaBadRequestException exception) {
        log.error(exception.getMessage(), exception);

        return NostalgiaErrorResponse.builder()
                .header(NostalgiaErrorResponse.Header.BAD_REQUEST.getName())
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(NostalgiaAlreadyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    NostalgiaErrorResponse handleAlreadyExistError(final NostalgiaAlreadyException exception) {
        log.error(exception.getMessage(), exception);

        return NostalgiaErrorResponse.builder()
                .header(NostalgiaErrorResponse.Header.ALREADY_EXIST.getName())
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(NostalgiaProcessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    NostalgiaErrorResponse handleProcessError(final NostalgiaProcessException exception) {
        log.error(exception.getMessage(), exception);

        return NostalgiaErrorResponse.builder()
                .header(NostalgiaErrorResponse.Header.PROCESS_ERROR.getName())
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    NostalgiaErrorResponse handleProcessError(final Exception exception) {
        log.error(exception.getMessage(), exception);

        return NostalgiaErrorResponse.builder()
                .header(NostalgiaErrorResponse.Header.PROCESS_ERROR.getName())
                .build();
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    NostalgiaErrorResponse handleEndpointNotFoundError(final NoResourceFoundException exception) {
        log.error(exception.getMessage(), exception);

        return NostalgiaErrorResponse.builder()
                .header(NostalgiaErrorResponse.Header.API_ERROR.getName())
                .build();
    }

    @ExceptionHandler(NostalgiaAuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    NostalgiaErrorResponse handleAuthError(final NostalgiaAuthException exception) {
        log.error(exception.getMessage(), exception);

        return NostalgiaErrorResponse.builder()
                .header(NostalgiaErrorResponse.Header.AUTH_ERROR.getName())
                .build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    NostalgiaErrorResponse handleAccessDeniedError(final AccessDeniedException exception) {
        log.error(exception.getMessage(), exception);

        return NostalgiaErrorResponse.builder()
                .header(NostalgiaErrorResponse.Header.AUTH_ERROR.getName())
                .build();
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    NostalgiaErrorResponse handleSQLError(final SQLException exception) {
        log.error(exception.getMessage(), exception);

        return NostalgiaErrorResponse.builder()
                .header(NostalgiaErrorResponse.Header.DATABASE_ERROR.getName())
                .build();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    NostalgiaErrorResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        log.error(exception.getMessage(), exception);

        return NostalgiaErrorResponse.builder()
                .header(NostalgiaErrorResponse.Header.VALIDATION_ERROR.getName())
                .build();
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    NostalgiaErrorResponse handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException exception) {

        log.error(exception.getMessage(), exception);

        return NostalgiaErrorResponse.builder()
                .header(NostalgiaErrorResponse.Header.VALIDATION_ERROR.getName())
                .build();
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    NostalgiaErrorResponse handleDataAccessException(DataAccessException exception) {

        log.error(exception.getMessage(), exception);

        return NostalgiaErrorResponse.builder()
                .header(NostalgiaErrorResponse.Header.DATABASE_ERROR.getName())
                .build();
    }

}
