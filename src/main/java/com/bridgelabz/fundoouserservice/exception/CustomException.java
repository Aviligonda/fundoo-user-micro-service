package com.bridgelabz.fundoouserservice.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Purpose : CustomException are Used to Validation exception
 * Version : 1.0
 * @author : Aviligonda Sreenivasulu
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomException {
    private int errorCode;
    private String message;
}
