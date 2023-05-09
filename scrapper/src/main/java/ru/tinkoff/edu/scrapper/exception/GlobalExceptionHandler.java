package ru.tinkoff.edu.scrapper.exception;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import ru.tinkoff.edu.scrapper.dto.response.ApiErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception ex, WebRequest request) {
        return ResponseEntity.badRequest().body(
                new ApiErrorResponse(
                    request.getDescription(false),
                    HttpStatus.BAD_REQUEST.toString(),
                    ex.getClass().getName(),
                    ex.getMessage(),
                    Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList())
        ));
    }
}
