package com.tttm.Whear.App.service.impl;

import com.tttm.Whear.App.constant.ConstantMessage;
import com.tttm.Whear.App.entity.Customer;
import com.tttm.Whear.App.entity.User;
import com.tttm.Whear.App.enums.ERole;
import com.tttm.Whear.App.enums.StatusGeneral;
import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.repository.UserRepository;
import com.tttm.Whear.App.service.CustomerService;
import com.tttm.Whear.App.service.UserService;
import com.tttm.Whear.App.utils.request.UserRequest;
import com.tttm.Whear.App.utils.response.CustomerResponse;
import com.tttm.Whear.App.utils.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final CustomerService customerService;

    private boolean checkValidArguement(UserRequest userRequest)
    {
        return !userRequest.getUsername().isEmpty() && !userRequest.getUsername().isBlank() &&
                !userRequest.getEmail().isEmpty() && !userRequest.getEmail().isBlank() &&
                !userRequest.getPassword().isEmpty() && !userRequest.getPassword().isBlank() &&
                !userRequest.getPhone().isEmpty() && !userRequest.getPhone().isBlank();
    }
    @Override
    public CustomerResponse createNewUsers(UserRequest userRequest) throws CustomException {
        if(!checkValidArguement(userRequest))
        {
            logger.error(ConstantMessage.MISSING_ARGUMENT.getMessage());
            throw new CustomException(ConstantMessage.MISSING_ARGUMENT.getMessage());
        }
        if(userRepository.getUserByUsername(userRequest.getUsername()) != null){
            logger.error(ConstantMessage.USERNAME_IS_EXIST.getMessage());
            throw new CustomException(ConstantMessage.USERNAME_IS_EXIST.getMessage());
        }
        if(userRepository.getUserByEmail(userRequest.getEmail()) != null){
            logger.error(ConstantMessage.EMAIL_IS_EXIST.getMessage());
            throw new CustomException(ConstantMessage.EMAIL_IS_EXIST.getMessage());
        }
        if(userRepository.getUserByPhone(userRequest.getPhone()) != null){
            logger.error(ConstantMessage.PHONE_IS_EXIST.getMessage());
            throw new CustomException(ConstantMessage.PHONE_IS_EXIST.getMessage());
        }

        User savedUser = userRepository.save(User
                .builder()
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .dateOfBirth(userRequest.getDateOfBirth())
                .phone(userRequest.getPhone())
                .email(userRequest.getEmail())
                .gender(userRequest.getGender())
                .role(ERole.CUSTOMER)
                .imgUrl(userRequest.getImgUrl())
                .status(StatusGeneral.ACTIVE)
                .language(userRequest.getLanguage())
                .build());

        Customer customer = customerService.createNewCustomers(savedUser);
        logger.info(ConstantMessage.CREATE_SUCCESS.getMessage());
        return convertToCustomerResponse(savedUser, customer);
    }

    @Override
    public UserResponse getUserbyUsername(String username) throws CustomException {
        Optional.of(username)
                .filter(userID -> !userID.isEmpty() && !userID.isBlank())
                .orElseThrow(() -> handleInvalidUsername(username));

        User user = Optional.ofNullable(userRepository.getUserByUsername(username))
                .orElseThrow(() -> handleUserNotFound(username));

        return convertToUserResponse(user);
    }

    @Override
    public List<UserResponse> getAllUser() throws CustomException {
        return userRepository.findAll()
                .stream()
                .map(this::convertToUserResponse)
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public UserResponse getUserByUsernameAndPassword(String username, String password) throws CustomException {
        if(username.isBlank() || username.isEmpty() || password.isEmpty() || password.isBlank())
        {
            logger.error(ConstantMessage.MISSING_ARGUMENT.getMessage());
            throw new CustomException(ConstantMessage.MISSING_ARGUMENT.getMessage());
        }

        User user = Optional.ofNullable(userRepository.getUserByUsernameAndPassword(username, password))
                .orElseThrow(() -> new CustomException(ConstantMessage.INVALID_USERNAME_OR_PASSWORD.getMessage()));

        return convertToUserResponse(user);
    }

    @Override
    public UserResponse updateUserByUsername(UserRequest userRequest) throws CustomException {
        Optional.of(userRequest.getUsername())
                .filter(userID -> !userID.isEmpty() && !userID.isBlank())
                .orElseThrow(() -> handleInvalidUsername(userRequest.getUsername()));

        if(!checkValidArguement(userRequest))
        {
            logger.error(ConstantMessage.MISSING_ARGUMENT.getMessage());
            throw new CustomException(ConstantMessage.MISSING_ARGUMENT.getMessage());
        }

        User user = Optional.ofNullable(userRepository.getUserByUsername(userRequest.getUsername()))
                .orElseThrow(() -> handleUserNotFound(userRequest.getUsername()));

        if(userRepository.getUserByEmail(userRequest.getEmail()) != null && userRepository.getUserByEmail(userRequest.getEmail()) != user)
        {
            logger.error(ConstantMessage.EMAIL_IS_EXIST.getMessage());
            throw new CustomException(ConstantMessage.EMAIL_IS_EXIST.getMessage());
        }

        if(userRepository.getUserByPhone(userRequest.getPhone()) != null && userRepository.getUserByPhone(userRequest.getPhone()) != user)
        {
            logger.error(ConstantMessage.PHONE_IS_EXIST.getMessage());
            throw new CustomException(ConstantMessage.PHONE_IS_EXIST.getMessage());
        }

        User updateUser = User
                .builder()
                .username(user.getUsername())
                .password(userRequest.getPassword())
                .dateOfBirth(userRequest.getDateOfBirth())
                .phone(userRequest.getPhone())
                .email(userRequest.getEmail())
                .gender(userRequest.getGender())
                .role(user.getRole())
                .imgUrl(userRequest.getImgUrl())
                .status(user.getStatus())
                .language(userRequest.getLanguage())
                .followList(user.getFollowList())
                .followingList(user.getFollowingList())
                .tokenList(user.getTokenList())
                .userPostList(user.getUserPostList())
                .userCollectionList(user.getUserCollectionList())
                .userReact(user.getUserReact())
                .userComments(user.getUserComments())
                .build();
        userRepository.save(updateUser);
        logger.info("Update User Information Successfully");
        return convertToUserResponse(updateUser);
    }

    @Override
    public UserResponse updateStatusUser(String username) throws CustomException {
        Optional.of(username)
                .filter(userID -> !userID.isEmpty() && !userID.isBlank())
                .orElseThrow(() -> handleInvalidUsername(username));

        User user = Optional.ofNullable(userRepository.getUserByUsername(username))
                .orElseThrow(() -> handleUserNotFound(username));

        if(user.getStatus().equals(StatusGeneral.ACTIVE))
        {
            user.setStatus(StatusGeneral.INACTIVE);
        }
        else user.setStatus(StatusGeneral.ACTIVE);
        userRepository.save(user);
        logger.info("Change Status User Successfully");
        return convertToUserResponse(user);
    }

    @Override
    public User getUserEntityByUsername(String username) throws CustomException {
        Optional.of(username)
                .filter(userID -> !userID.isEmpty() && !userID.isBlank())
                .orElseThrow(() -> handleInvalidUsername(username));

        User user = Optional.ofNullable(userRepository.getUserByUsername(username))
                .orElseThrow(() -> handleUserNotFound(username));

        return user;
    }
    private CustomException handleInvalidUsername(String username)
    {
        logger.error(ConstantMessage.USERNAME_IS_EMPTY_OR_NOT_EXIST.getMessage());
        return new CustomException(ConstantMessage.USERNAME_IS_EMPTY_OR_NOT_EXIST.getMessage());
    }

    private CustomException handleUserNotFound(String username)
    {
        logger.error(ConstantMessage.CANNOT_FIND_USER_BY_USERNAME.getMessage());
        return new CustomException(ConstantMessage.CANNOT_FIND_USER_BY_USERNAME.getMessage());
    }

    @Override
    public List<User> getAllUserEntity() throws CustomException {
        return userRepository.findAll()
                .stream()
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public UserResponse convertToUserResponse(User user)
    {
        return UserResponse
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .dateOfBirth(user.getDateOfBirth())
                .phone(user.getPhone())
                .email(user.getEmail())
                .gender(user.getGender())
                .role(user.getRole())
                .imgUrl(user.getImgUrl())
                .status(user.getStatus())
                .language(user.getLanguage())
                .build();
    }

    @Override
    public CustomerResponse convertToCustomerResponse(User user, Customer customer)
    {
        return CustomerResponse
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .dateOfBirth(user.getDateOfBirth())
                .phone(user.getPhone())
                .email(user.getEmail())
                .gender(user.getGender())
                .role(user.getRole())
                .imgUrl(user.getImgUrl())
                .status(user.getStatus())
                .language(user.getLanguage())
                .isFirstLogin(customer.getIsFirstLogin())
                .subRole(customer.getSubRole())
                .build();
    }
}
