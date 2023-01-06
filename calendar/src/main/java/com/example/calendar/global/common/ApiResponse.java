package com.example.calendar.global.common;

import lombok.Builder;
import org.springframework.http.HttpStatus;

public class ApiResponse<T>{
    private int result;		//error일 때 false
    //private ErrorResponse error;	//error가 아닐 때 null
    private T data;   		//error일 때 null

    @Builder
    public ApiResponse(HttpStatus code, /*ErrorResponse error,*/ T data) {
        this.result = code.value();
        //this.error = error;
        this.data = data;
    }
}
