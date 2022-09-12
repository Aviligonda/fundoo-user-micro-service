package com.bridgelabz.fundoouserservice.service;

import com.bridgelabz.fundoouserservice.dto.UserServiceDTO;
import com.bridgelabz.fundoouserservice.model.UserServiceModel;
import com.bridgelabz.fundoouserservice.util.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
/*
 * Purpose : IUserService to Show The all APIs
 * Version : 1.0
 * @author : Aviligonda Sreenivasulu
 * */
public interface IUserService {
    Response createUser(UserServiceDTO userServiceDTO);

    Response updateUser(UserServiceDTO userServiceDTO, String token, Long id);

    List<UserServiceModel> getAllUsers(String token);

    Response loginUser(String emailId, String password);

    Response changePassword(String emailId, String oldPassword, String newPassword, String token);

    Response resetPassword(String emailId);

    Response deleteUser(Long id, String token);

    Response restoreUser(Long id, String token);

    Response permanentDelete(Long id, String token);

    Response addProfilePic(String token, Long id, String profilePic);

    Response addProfile(Long id, MultipartFile profilePic);

}
