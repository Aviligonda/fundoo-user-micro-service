package com.bridgelabz.fundoouserservice.util;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
 * Purpose :Return Status
 * Version : 1.0
 * @author : Aviligonda Sreenivasulu
 *
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private int statusCode;
    private String statusMessage;
    private Object object;
}
