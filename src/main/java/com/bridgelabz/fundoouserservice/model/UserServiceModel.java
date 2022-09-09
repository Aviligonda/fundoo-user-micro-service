package com.bridgelabz.fundoouserservice.model;

import com.bridgelabz.fundoouserservice.dto.UserServiceDTO;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
/*
 * Purpose : UserServiceModel Are Used Create A table and connection to Database
 * Version : 1.0
 * @author : Aviligonda Sreenivasulu
 * */
@Data
@Entity
@Table(name = "userService")
public class UserServiceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String emailId;
    private String password;
    private boolean isActive;
    private boolean isDeleted;
    private LocalDate dateOfBirth;
    private long phoneNumber;
    private String profilePic;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    public UserServiceModel() {
    }

    public UserServiceModel(UserServiceDTO userServiceDTO) {
        this.name = userServiceDTO.getName();
        this.emailId = userServiceDTO.getEmailId();
        this.password = userServiceDTO.getPassword();
        this.isActive = userServiceDTO.isActive();
        this.isDeleted = userServiceDTO.isDeleted();
        this.dateOfBirth = userServiceDTO.getDateOfBirth();
        this.phoneNumber = userServiceDTO.getPhoneNumber();
    }
}
