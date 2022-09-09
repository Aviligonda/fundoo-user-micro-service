package com.bridgelabz.fundoouserservice.exception;

import com.bridgelabz.fundoouserservice.util.ResponseClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*
 * Purpose : UserExceptionHandler to Handle the Exceptions
 * Version : 1.0
 * @author : Aviligonda Sreenivasulu
 * */
@ControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ResponseClass> handleHiringException(UserException exception) {
        ResponseClass response = new ResponseClass();
        response.setErrorCode(400);
        response.setMessage(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*
     * Purpose : MethodArgumentNotValidException to Handle the Validation Exceptions
     * Version : 1.0
     * @author : Aviligonda Sreenivasulu
     * */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomException> handleHiringException(MethodArgumentNotValidException exception) {
        CustomException response = new CustomException();
        response.setErrorCode(400);
        response.setMessage(exception.getFieldError().getDefaultMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
