package com.bridgelabz.fundoouserservice.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Purpose : ResponseClass Used to Handle Exception
 * Version : 1.0
 * @author : Aviligonda Sreenivasulu
 * */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseClass {
    private int errorCode;
    private String message;
    private Object token;
}
