package com.example.calendar.global.advice;

import com.example.calendar.global.common.ApiResponse;
import com.example.calendar.global.error.ErrorResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {

        if(body instanceof ErrorResponse) {
            ErrorResponse errorResponse = (ErrorResponse) body;
            ApiResponse<Object> apiResponse = ApiResponse.builder().code(HttpStatus.BAD_REQUEST).error(errorResponse).build();
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }

        ApiResponse<Object> apiResponse = ApiResponse.builder().code(HttpStatus.OK).data(body).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
