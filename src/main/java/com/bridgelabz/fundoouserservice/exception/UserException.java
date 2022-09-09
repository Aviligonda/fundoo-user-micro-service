package com.bridgelabz.fundoouserservice.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
/*
 * Purpose : UserException to Handle the Exceptions
 * Version : 1.0
 * @author : Aviligonda Sreenivasulu
 * */
@ResponseStatus
public class UserException extends RuntimeException {
    private int code;
    private String status;

    public UserException(int code, String status) {
        super(status);
        this.code = code;
        this.status = status;
    }
}
