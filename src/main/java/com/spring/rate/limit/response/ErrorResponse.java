package com.spring.rate.limit.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private String message;
    private List<String> errors;
    private HttpStatus status;

    public ErrorResponse(String exceptionMessage, HttpStatus status) {
        this.status = status;
        this.message = exceptionMessage;
    }

    public ErrorResponse(String exceptionMessage, HttpStatus status, List<String> errors) {
        this.status = status;
        this.message = exceptionMessage;
        this.errors = errors;
    }
}
