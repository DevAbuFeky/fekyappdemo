package com.demoappfeky.exceptionhandle;

import com.demoappfeky.exceptionhandle.AppErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ControlExceptionHandler extends RuntimeException{

    public ControlExceptionHandler(String message) {
        super(message);
    }

    public ControlExceptionHandler(String message, Throwable cause) {
        super(message, cause);
    }

    public ControlExceptionHandler(Throwable cause) {
        super(cause);
    }


}
