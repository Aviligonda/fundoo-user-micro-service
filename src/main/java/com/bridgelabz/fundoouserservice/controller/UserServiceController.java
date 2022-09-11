package com.bridgelabz.fundoouserservice.controller;

import com.bridgelabz.fundoouserservice.dto.UserServiceDTO;
import com.bridgelabz.fundoouserservice.model.UserServiceModel;
import com.bridgelabz.fundoouserservice.service.IUserService;
import com.bridgelabz.fundoouserservice.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.util.List;

/*
 * Purpose :REST ApIs Controller
 * Version : 1.0
 * @author : Aviligonda Sreenivasulu
 *
 * */
@RestController
@RequestMapping("userService")
public class UserServiceController {
    @Autowired
    IUserService userService;

    /*
     * Purpose : User Details Create
     * @author : Aviligonda Sreenivasulu
     * @Param : userServiceDTO
     * */
    @PostMapping("/create")
    public ResponseEntity<Response> creatingUser(@Valid @RequestBody UserServiceDTO userServiceDTO) {
        Response response = userService.createUser(userServiceDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Purpose : Existing User Details Update
     * @author : Aviligonda Sreenivasulu
     * @Param :  token,userServiceDTO and id
     * */
    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateUser(@PathVariable Long id,
                                               @RequestHeader String token,
                                               @Valid @RequestBody UserServiceDTO userServiceDTO) {
        Response response = userService.updateUser(userServiceDTO, token, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Purpose : Retrive All User Details
     * @author : Aviligonda Sreenivasulu
     * @Param : token
     * */
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<?>> getAllUsers(@RequestHeader String token) {
        List<UserServiceModel> response = userService.getAllUsers(token);
        return new ResponseEntity<List<?>>(response, HttpStatus.OK);
    }

    /*
     * Purpose : Login  with admin Email and Password
     * @author : Aviligonda Sreenivasulu
     * @Param : emailId and password
     * */
    @PostMapping("/login")
    public ResponseEntity<Response> loginUser(@RequestParam String emailId,
                                              @RequestHeader String password) {
        Response response = userService.loginUser(emailId, password);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Purpose : Update Login Password
     * @author : Aviligonda Sreenivasulu
     * @Param : token and password
     * */
    @PutMapping("/changePassword")
    public ResponseEntity<Response> changePassword(@RequestParam String emailId,
                                                   @RequestParam String oldPassword,
                                                   @RequestParam String newPassword,
                                                   @RequestHeader String token) {
        Response response = userService.changePassword(emailId, oldPassword, newPassword, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Purpose : Reset Login Password
     * @author : Aviligonda Sreenivasulu
     * @Param : emailId
     * */
    @PostMapping("/resetPassword")
    public ResponseEntity<Response> resetPassword(@RequestParam String emailId) {
        Response response = userService.resetPassword(emailId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    /*
     * Purpose : Delete user Details
     * @author : Aviligonda Sreenivasulu
     * @Param : token and id
     * */

    @PutMapping("/deleteUser/{id}")
    public ResponseEntity<Response> deleteUser(@PathVariable Long id,
                                               @RequestHeader String token) {
        Response response = userService.deleteUser(id, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Purpose : Restore user Details
     * @author : Aviligonda Sreenivasulu
     * @Param : token and id
     * */
    @PutMapping("/restoreUser/{id}")
    public ResponseEntity<Response> restoreUser(@PathVariable Long id,
                                                @RequestHeader String token) {
        Response response = userService.restoreUser(id, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Purpose : permanentDelete user Details
     * @author : Aviligonda Sreenivasulu
     * @Param : token and id
     * */
    @DeleteMapping("/permanentDelete/{id}")
    public ResponseEntity<Response> permanentDelete(@PathVariable Long id,
                                                    @RequestHeader String token) {
        Response response = userService.permanentDelete(id, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * Purpose : Set Profile Path of User
     * @author : Aviligonda Sreenivasulu
     * @Param :  profilePath,id and token
     * */
    @PutMapping("/profilePic/{id}")
    public ResponseEntity<Response> profilePic(@RequestHeader String token,
                                               @PathVariable Long id,
                                               @RequestParam String profilePic) {
        Response response = userService.addProfilePic(token, id, profilePic);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/addProfilePic/{id}")
    public ResponseEntity<Response> addProfilePic(@PathVariable Long id,
                                                  @RequestParam(value = "File") File profilePic) {
        Response response = userService.addProfile(id, profilePic);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
