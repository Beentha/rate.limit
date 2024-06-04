package com.spring.rate.limit.configs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.rate.limit.domain.exceptions.NotImplementedException;
import com.spring.rate.limit.domain.exceptions.RateLimitException;
import com.spring.rate.limit.domain.exceptions.ServerErrorException;
import com.spring.rate.limit.domain.exceptions.UnauthorizedException;
import com.spring.rate.limit.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class RestControllerAdvice extends ResponseEntityExceptionHandler {

    private static final String DEFAULT_ERROR_MESSAGE = "An unexpected error occurred, please try again!";
    private static final String RATE_LIMIT_ERROR_MESSAGE = "You have exceeded the maximum number of allowed requests, please try again!";
    public static final String EXCEPTION_LOG_ERROR_MESSAGE = "An exception occurred: {}";

    private final ObjectMapper mapper;

    @SneakyThrows
    @ExceptionHandler(
            value = RateLimitException.class
    )
    public ResponseEntity<Object> handleRateLimitException(RateLimitException exception, WebRequest webRequest) {
        return handleException(exception, DEFAULT_ERROR_MESSAGE, webRequest, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @SneakyThrows
    @ExceptionHandler(
            value = ServerErrorException.class
    )
    public ResponseEntity<Object> handleServerErrorException(RateLimitException exception, WebRequest webRequest) {
        return handleException(exception, RATE_LIMIT_ERROR_MESSAGE, webRequest, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @SneakyThrows
    @ExceptionHandler(NotImplementedException.class)
    public ResponseEntity<Object> handleNotImplementedException(RuntimeException exception, WebRequest webRequest) {
        return handleException(exception, exception.getMessage(), webRequest, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @SneakyThrows
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(RuntimeException exception, WebRequest webRequest) {
        return handleException(exception, exception.getMessage(), webRequest, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @SneakyThrows
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception exception, WebRequest webRequest) {
        log.error(EXCEPTION_LOG_ERROR_MESSAGE, exception.getMessage(), exception);

        ErrorResponse responseBody = new ErrorResponse(DEFAULT_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        HttpHeaders httpHeaders = buildHeaders();

        return handleExceptionInternal(
                exception,
                mapper.writeValueAsString(responseBody),
                httpHeaders,
                HttpStatus.INTERNAL_SERVER_ERROR,
                webRequest);
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

    private ResponseEntity<Object> handleException(RuntimeException exception,
                                                   String errorMessage,
                                                   WebRequest webRequest,
                                                   HttpStatus httpStatus) throws JsonProcessingException {
        log.error(EXCEPTION_LOG_ERROR_MESSAGE, exception.getMessage(), exception);

        ErrorResponse responseBody = new ErrorResponse(errorMessage, httpStatus);
        HttpHeaders httpHeaders = buildHeaders();

        return handleExceptionInternal(
                exception,
                mapper.writeValueAsString(responseBody),
                httpHeaders,
                httpStatus,
                webRequest);
    }
}
