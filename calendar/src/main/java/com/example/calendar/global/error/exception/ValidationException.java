package com.example.calendar.global.error.exception;

import com.example.calendar.global.common.ValidationErrorResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
@Getter
@Slf4j
public class ValidationException extends RuntimeException {
    private List<ValidationErrorResponse> validationErrors;

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(List<ValidationErrorResponse> errors, String requestId) {
        this.validationErrors = errors;
        log.warn("The following request {} has validation exceptions", requestId);
    }
}
