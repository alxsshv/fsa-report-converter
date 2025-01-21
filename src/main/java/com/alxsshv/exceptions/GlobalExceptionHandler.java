package com.alxsshv.exceptions;

import com.alxsshv.utils.ServiceMessage;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> catchEmployeeNotFoundException(EmployeeNotFoundException ex){
        log.error(ex.getMessage());
        return ResponseEntity.status(400).body(new ServiceMessage(ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<?> catchIOException(IOException ex){
        log.error(ex.getMessage());
        return ResponseEntity.status(500).body(new ServiceMessage(ex.getMessage()));
    }

    @ExceptionHandler
    public void catchHttpMessageNotWritableException(HttpMessageNotWritableException ex){
        log.error(ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<?> catchArshinResponseException(ArshinResponseException ex){
        log.error(ex.getMessage());
        return ResponseEntity.status(500).body(new ServiceMessage(ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<?> catchConstraintViolationException(ConstraintViolationException ex){
        log.error(ex.getMessage());
        String[] exMessage = ex.getMessage().split(":");
        String errorMessage = exMessage[1];
        if (exMessage[0].contains("parse.arshinObjects")){
          errorMessage = createMessage(ex.getMessage());
        }
        return ResponseEntity.status(400).body(new ServiceMessage(errorMessage));
    }

    private String createMessage(String exceptionMessage){
        String rawNumString;
        int rawNumberBeginIndex = exceptionMessage.indexOf("[")+1;
        int rawNumberEndIndex = exceptionMessage.indexOf("]");
        if (rawNumberBeginIndex == rawNumberEndIndex){
            rawNumString = exceptionMessage.substring(rawNumberBeginIndex, rawNumberBeginIndex+1);
        } else {
            rawNumString = exceptionMessage.substring(rawNumberBeginIndex, rawNumberEndIndex);
        }
        int rawNumber = Integer.parseInt(rawNumString)+1;
        return  "Ошибка в файле! Строка № " + rawNumber + ": " + exceptionMessage.split(":")[1];
    }
}
