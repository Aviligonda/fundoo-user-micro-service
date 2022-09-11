package com.bridgelabz.fundoouserservice.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
/*
 * Purpose : UserDTO fields are Used to Create and Update User Details
 * Version : 1.0
 * @author : Aviligonda Sreenivasulu
 * */
@Data
public class UserServiceDTO {
    @Pattern(regexp = "[A-Z][a-z]{2,}", message = "Invalid name")
    private String name;
    @Pattern(regexp = "(\\w+[.+-]?)*@\\w+(\\.+[a-zA-Z]{2,4})*", message = "Invalid Email, Enter correct Email")
    private String emailId;
    @Pattern(regexp = "(?=.*?[A-Z])(?=.*?\\d)(?=.*?[!@#$%^&*_()+-])[A-Za-z\\d!@#$%^&()*+_-]{8,}"
            , message = "Password should have AtLeast one (capital ,small,special character,numeric) minimum 8 characters")
    private String password;
    @NotNull(message = " dateOfBirth can't be Empty")
    private LocalDate dateOfBirth;
    @NotNull(message = " phoneNumber can't be Empty")
    private long phoneNumber;
}
