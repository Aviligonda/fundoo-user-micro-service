package com.bridgelabz.fundoouserservice.service;

import com.bridgelabz.fundoouserservice.dto.UserServiceDTO;
import com.bridgelabz.fundoouserservice.exception.UserException;
import com.bridgelabz.fundoouserservice.model.UserServiceModel;
import com.bridgelabz.fundoouserservice.repository.UserServiceRepository;
import com.bridgelabz.fundoouserservice.util.Response;
import com.bridgelabz.fundoouserservice.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/*
 * Purpose : AdminService to Implement the Business Logic
 * Version : 1.0
 * @author : Aviligonda Sreenivasulu
 * */
@Service
public class UserService implements IUserService {
    @Autowired
    MailService mailService;
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    UserServiceRepository userServiceRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    /*
     * Purpose : Implement the Logic of Creating User Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  adminDTO
     * */
    @Override
    public Response createUser(UserServiceDTO userServiceDTO) {
        UserServiceModel userServiceModel = new UserServiceModel(userServiceDTO);
        userServiceModel.setCreatedTime(LocalDateTime.now());
        userServiceModel.setPassword(passwordEncoder.encode(userServiceDTO.getPassword()));
        userServiceModel.setDeleted(false);
        userServiceModel.setActive(true);
        userServiceRepository.save(userServiceModel);
        String body = "User Added Successfully with user id is :" + userServiceModel.getId();
        String subject = "User Registration Successfully";
        mailService.send(userServiceModel.getEmailId(), body, subject);
        return new Response(200, "Success", userServiceModel);
    }

    /*
     * Purpose : Implement the Logic of Update User Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  token,id and adminDTO
     * */
    @Override
    public Response updateUser(UserServiceDTO userServiceDTO, String token, Long id) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserServiceModel> isUserPresent = userServiceRepository.findById(userId);
        if (isUserPresent.isPresent()) {
            Optional<UserServiceModel> isIdPresent = userServiceRepository.findById(id);
            if (isIdPresent.isPresent()) {
                isIdPresent.get().setName(userServiceDTO.getName());
                isIdPresent.get().setEmailId(userServiceDTO.getEmailId());
                isIdPresent.get().setDateOfBirth(userServiceDTO.getDateOfBirth());
                isIdPresent.get().setPhoneNumber(userServiceDTO.getPhoneNumber());
                isIdPresent.get().setUpdatedTime(LocalDateTime.now());
                userServiceRepository.save(isIdPresent.get());
                String body = "User Updated Successfully with user id is :" + isIdPresent.get().getId();
                String subject = "User Updated Successfully";
                mailService.send(isIdPresent.get().getEmailId(), body, subject);
                return new Response(200, "Success", isIdPresent.get());
            } else {
                throw new UserException(400, "No User Found with this id");
            }
        }
        throw new UserException(400, "Token is Wrong");
    }

    /*
     * Purpose : Implement the Logic of Get All User Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  token
     * */
    @Override
    public List<UserServiceModel> getAllUsers(String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserServiceModel> isUserPresent = userServiceRepository.findById(userId);
        if (isUserPresent.isPresent()) {
            List<UserServiceModel> isUsersPresent = userServiceRepository.findAll();
            if (isUsersPresent.size() > 0) {
                return isUsersPresent;
            } else {
                throw new UserException(400, "No users Found");
            }

        }
        throw new UserException(400, "Token is Wrong");
    }

    /*
     * Purpose : Implement the Logic of Login credentials
     * @author : Aviligonda Sreenivasulu
     * @Param :  emailId and password
     * */
    @Override
    public Response loginUser(String emailId, String password) {
        Optional<UserServiceModel> isEmailPresent = userServiceRepository.findByEmailId(emailId);
        if (isEmailPresent.isPresent()) {
//            if (isEmailPresent.get().getPassword().equals(password)) {
            if (passwordEncoder.matches(password, isEmailPresent.get().getPassword())) {
                String token = tokenUtil.createToken(isEmailPresent.get().getId());
                return new Response(200, "Login Success", token);
            } else {
                throw new UserException(400, "Wrong Password");
            }
        } else {
            throw new UserException(400, "Not found this EmailId");
        }
    }

    /*
     * Purpose : Implement the Logic of Update Password
     * @author : Aviligonda Sreenivasulu
     * @Param :  token and password
     * */
    @Override
    public Response changePassword(String emailId, String oldPassword, String newPassword, String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserServiceModel> isUserPresent = userServiceRepository.findById(userId);
        if (isUserPresent.isPresent()) {
            Optional<UserServiceModel> isEmailPresent = userServiceRepository.findByEmailId(emailId);
            if (isEmailPresent.isPresent()) {
                if (isEmailPresent.get().getPassword().equals(oldPassword)) {
                    isEmailPresent.get().setPassword(passwordEncoder.encode(newPassword));
                    userServiceRepository.save(isEmailPresent.get());
                    return new Response(200, "Success", isEmailPresent.get());
                } else {
                    throw new UserException(400, "Password is wrong");
                }
            }
            throw new UserException(400, "Email is not found");
        }
        throw new UserException(400, "Token is Wrong");
    }

    /*
     * Purpose : Implement the Logic of Reset Password
     * @author : Aviligonda Sreenivasulu
     * @Param :  emailId
     * */
    @Override
    public Response resetPassword(String emailId) {
        Optional<UserServiceModel> isEmailPresent = userServiceRepository.findByEmailId(emailId);
        if (isEmailPresent.isPresent()) {
            String token = tokenUtil.createToken(isEmailPresent.get().getId());
            String url = System.getenv("url");
            String subject = "Reset Password";
            String body = " Reset password Use this link \n" + url + "\n Use this token to reset \n" + token;
            mailService.send(isEmailPresent.get().getEmailId(), body, subject);
            userServiceRepository.save(isEmailPresent.get());
            return new Response(200, "Success", "Check Your Register Mail");
        } else {
            throw new UserException(400, "Email is not found");
        }
    }

    /*
     * Purpose : Implement the Logic of Delete user Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  token and id
     * */
    @Override
    public Response deleteUser(Long id, String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserServiceModel> isUserPresent = userServiceRepository.findById(userId);
        if (isUserPresent.isPresent()) {
            Optional<UserServiceModel> isIdPresent = userServiceRepository.findById(id);
            if (isIdPresent.isPresent()) {
                if (isIdPresent.get().isActive()) {
                    isIdPresent.get().setActive(false);
                    isIdPresent.get().setDeleted(true);
                    userServiceRepository.save(isIdPresent.get());
                    return new Response(200, "Success", isIdPresent.get());
                } else {
                    throw new UserException(400, "This User not in active ");
                }
            } else {
                throw new UserException(400, "Not found with this id ");
            }
        }
        throw new UserException(400, "Token is wrong");
    }

    /*
     * Purpose : Implement the Logic of restore Admin Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  token and id
     * */
    @Override
    public Response restoreUser(Long id, String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserServiceModel> isUserPresent = userServiceRepository.findById(userId);
        if (isUserPresent.isPresent()) {
            Optional<UserServiceModel> isIdPresent = userServiceRepository.findById(id);
            if (isIdPresent.isPresent()) {
                if (isIdPresent.get().isDeleted()) {
                    isIdPresent.get().setActive(true);
                    isIdPresent.get().setDeleted(false);
                    userServiceRepository.save(isIdPresent.get());
                    return new Response(200, "Success", isIdPresent.get());
                } else {
                    throw new UserException(400, "This Id Not in Deleted ");
                }
            } else {
                throw new UserException(400, "Not found with this id");
            }
        }
        throw new UserException(400, "Token is wrong");
    }

    /*
     * Purpose : Implement the Logic of PermanentDelete user Details
     * @author : Aviligonda Sreenivasulu
     * @Param :  token and id
     * */
    @Override
    public Response permanentDelete(Long id, String token) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserServiceModel> isUserPresent = userServiceRepository.findById(userId);
        if (isUserPresent.isPresent()) {
            Optional<UserServiceModel> isIdPresent = userServiceRepository.findById(id);
            if (isIdPresent.isPresent()) {
                if (isIdPresent.get().isDeleted()) {
                    userServiceRepository.delete(isIdPresent.get());
                    return new Response(200, "Success", isIdPresent.get());
                } else {
                    throw new UserException(400, "The User Not in delete ");
                }
            } else {
                throw new UserException(400, "Not found with this id");
            }
        }
        throw new UserException(400, "Token is wrong");
    }

    /*
     * Purpose : Implement the Logic of Adding the ProfilePath
     * @author : Aviligonda Sreenivasulu
     * @Param :  token,id and profilePath
     * */
    @Override
    public Response addProfilePic(String token, Long id, String profilePic) {
        Long userId = tokenUtil.decodeToken(token);
        Optional<UserServiceModel> isUserPresent = userServiceRepository.findById(userId);
        if (isUserPresent.isPresent()) {
            Optional<UserServiceModel> isIdPresent = userServiceRepository.findById(id);
            if (isIdPresent.isPresent()) {
//                isIdPresent.get().setProfilePic(profilePic);
                userServiceRepository.save(isIdPresent.get());
                return new Response(200, "success", isIdPresent.get());
            } else {
                throw new UserException(400, "Not found With this id");
            }
        } else {
            throw new UserException(400, "Token is Wrong");
        }
    }

    @Override
    public Response addProfile(Long id, MultipartFile profilePic) {
        Optional<UserServiceModel> isIdPresent = userServiceRepository.findById(id);
        if (isIdPresent.isPresent()) {
            isIdPresent.get().setProfilePic((File) profilePic);
            return new Response(200, "Success", isIdPresent.get());
        }
        return null;
    }
}
