package com.backend.bookstore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.bookstore.dto.UserProfileDTO;
import com.backend.bookstore.entity.User;

@Service
public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    User saveUser(User user);

    User updateUser(User user, Long id);

    void deleteUser(Long id);

    User updatUserProfile(UserProfileDTO userProfileDTO, Long id);

    User updateExistingUserRole(User user, Long id);

}
