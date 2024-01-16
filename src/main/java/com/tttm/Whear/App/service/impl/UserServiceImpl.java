package com.tttm.Whear.App.service.impl;

import com.tttm.Whear.App.constant.ConstantMessage;
import com.tttm.Whear.App.entity.Customer;
import com.tttm.Whear.App.entity.User;
import com.tttm.Whear.App.enums.ERole;
import com.tttm.Whear.App.enums.StatusGeneral;
import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.repository.SubRoleRepository;
import com.tttm.Whear.App.repository.UserRepository;
import com.tttm.Whear.App.service.CustomerService;
import com.tttm.Whear.App.service.UserService;
import com.tttm.Whear.App.utils.request.UserRequest;
import com.tttm.Whear.App.utils.response.CustomerResponse;
import com.tttm.Whear.App.utils.response.UserResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  private final UserRepository userRepository;

  private final CustomerService customerService;
  private final SubRoleRepository subRoleRepository;

  private boolean checkValidArguement(UserRequest userRequest) {

    return
        userRequest != null &&

        userRequest.getUserID() != null &&
        userRequest.getEmail() != null &&
        userRequest.getPhone() != null &&

        !userRequest.getUserID().isEmpty() && !userRequest.getUserID().isBlank() &&
        !userRequest.getEmail().isEmpty() && !userRequest.getEmail().isBlank() &&
        !userRequest.getPhone().isEmpty() && !userRequest.getPhone().isBlank();
  }


  @Override
//  @Caching(
//          evict = @CacheEvict(cacheNames = "users", allEntries = true),
//          cacheable = @Cacheable(cacheNames = "user", key = "#userRequest.username", unless = "#result == null")
//  )
  public CustomerResponse createNewUsers(UserRequest userRequest) throws CustomException {
    if (!(!userRequest.getEmail().isEmpty() && !userRequest.getEmail().isBlank() &&
        !userRequest.getPassword().isEmpty() && !userRequest.getPassword().isBlank() &&
        !userRequest.getPhone().isEmpty() && !userRequest.getPhone().isBlank())) {
      logger.error(ConstantMessage.MISSING_ARGUMENT.getMessage());
      throw new CustomException(ConstantMessage.MISSING_ARGUMENT.getMessage());
    }
    if (userRepository.getUserByEmail(userRequest.getEmail()) != null) {
      logger.error(ConstantMessage.EMAIL_IS_EXIST.getMessage());
      throw new CustomException(ConstantMessage.EMAIL_IS_EXIST.getMessage());
    }
    if (userRepository.getUserByPhone(userRequest.getPhone()) != null) {
      logger.error(ConstantMessage.PHONE_IS_EXIST.getMessage());
      throw new CustomException(ConstantMessage.PHONE_IS_EXIST.getMessage());
    }
    String userID = String.valueOf(userRepository.count()+1);
    User savedUser = userRepository.save(User
        .builder()
        .userID(userID)
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
//  @Cacheable(cacheNames = "user", key = "#username", condition = "#username != null", unless = "#result == null")
  public List<UserResponse> getUserbyUsername(String username) throws CustomException {
    List<UserResponse> resultList = null;
    List<User> listUser = userRepository.findAll();
    for(User u : listUser){
      if(u.getUsername().toLowerCase().trim().contains(username.toLowerCase().trim())){
        if(resultList == null){
          resultList = new ArrayList<>();
        }
        resultList.add(convertToUserResponse(u));
      }
    }
    return resultList;
  }


  @Override
//  @Cacheable(cacheNames = "users")
  public List<UserResponse> getAllUser() throws CustomException {
    return userRepository.findAll()
        .stream()
        .map(this::convertToUserResponse)
        .filter(Objects::nonNull)
        .toList();
  }

  @Override
  public UserResponse getUserByUserEmailAndPassword(String email, String password)
      throws CustomException {
    if (email.isBlank() || email.isEmpty() || password.isEmpty() || password.isBlank()) {
      logger.error(ConstantMessage.MISSING_ARGUMENT.getMessage());
      throw new CustomException(ConstantMessage.MISSING_ARGUMENT.getMessage());
    }

    User user = Optional.ofNullable(userRepository.getUserByEmailAndPassword(email, password))
        .orElseThrow(
            () -> new CustomException(ConstantMessage.INVALID_USERNAME_OR_PASSWORD.getMessage()));

    return convertToUserResponse(user);
  }

  @Override
//  @Caching(
//          evict = @CacheEvict(cacheNames = "users", allEntries = true),
//          put = @CachePut(cacheNames = "user", key = "#userRequest.username", unless = "#result == null")
//  )
  public UserResponse updateUserByUserID(UserRequest userRequest) throws CustomException {
    if (!checkValidArguement(userRequest)) {
      logger.error(ConstantMessage.MISSING_ARGUMENT.getMessage());
      throw new CustomException(ConstantMessage.MISSING_ARGUMENT.getMessage());
    }

    User user = userRepository.getUserByUserID(userRequest.getUserID());
    if(user == null){
      throw new CustomException(ConstantMessage.USERID_IS_EMPTY_OR_NOT_EXIST.getMessage());
    }

    if (userRepository.getUserByEmail(userRequest.getEmail()) != null
        && userRepository.getUserByEmail(userRequest.getEmail()) != user) {
      logger.error(ConstantMessage.EMAIL_IS_EXIST.getMessage());
      throw new CustomException(ConstantMessage.EMAIL_IS_EXIST.getMessage());
    }

    if (userRepository.getUserByPhone(userRequest.getPhone()) != null
        && userRepository.getUserByPhone(userRequest.getPhone()) != user) {
      logger.error(ConstantMessage.PHONE_IS_EXIST.getMessage());
      throw new CustomException(ConstantMessage.PHONE_IS_EXIST.getMessage());
    }

    User updateUser = User
        .builder()
        .userID(user.getUserID())
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
        .build();
    userRepository.save(updateUser);
    logger.info("Update User Information Successfully");
    return convertToUserResponse(updateUser);
  }

  @Override
//  @Caching(
//          evict = @CacheEvict(cacheNames = "users", allEntries = true),
//          put = @CachePut(cacheNames = "user", key = "#username", unless = "#result == null")
//  )
  public UserResponse updateStatusUser(String userID) throws CustomException {
    Optional.of(userID)
        .filter(id -> !id.isEmpty() && !id.isBlank())
        .orElseThrow(() -> handleInvalidUserID(userID));

    User user = Optional.ofNullable(userRepository.getUserByUserID(userID))
        .orElseThrow(() -> handleUserNotFound(userID));

    if (user.getStatus().equals(StatusGeneral.ACTIVE)) {
      user.setStatus(StatusGeneral.INACTIVE);
    } else {
      user.setStatus(StatusGeneral.ACTIVE);
    }
    userRepository.save(user);
    logger.info("Change Status User Successfully");
    return convertToUserResponse(user);
  }

  @Override
  public User getUserEntityByUserID(String userID) throws CustomException {
    Optional.of(userID)
        .filter(id -> !id.isEmpty() && !id.isBlank())
        .orElseThrow(() -> handleInvalidUserID(userID));

    User user = Optional.ofNullable(userRepository.getUserByUserID(userID))
        .orElseThrow(() -> handleUserNotFound(userID));

    return user;
  }

  private CustomException handleInvalidUserID(String username) {
    logger.error(ConstantMessage.USERID_IS_EMPTY_OR_NOT_EXIST.getMessage());
    return new CustomException(ConstantMessage.USERID_IS_EMPTY_OR_NOT_EXIST.getMessage());
  }

  private CustomException handleUserNotFound(String username) {
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

  public UserResponse convertToUserResponse(User user) {
    return UserResponse
        .builder()
        .userID(user.getUserID())
        .username(user.getUsername())
        .password(user.getPassword())
        .dateOfBirth(user.getDateOfBirth().toString())
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
  public CustomerResponse convertToCustomerResponse(User user, Customer customer) {
    return CustomerResponse
        .builder()
        .userID(user.getUserID())
        .username(user.getUsername())
        .password(user.getPassword())
        .dateOfBirth(user.getDateOfBirth().toString())
        .phone(user.getPhone())
        .email(user.getEmail())
        .gender(user.getGender())
        .role(user.getRole())
        .imgUrl(user.getImgUrl())
        .status(user.getStatus())
        .language(user.getLanguage())
        .isFirstLogin(customer.getIsFirstLogin())
        .subRole(subRoleRepository.getSubRolesBySubRoleID(customer.getSubRoleID()).getSubRoleName())
        .build();
  }
}
