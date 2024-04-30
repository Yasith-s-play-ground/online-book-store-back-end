package com.backend.bookstore.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.bookstore.dto.UserProfileDTO;
import com.backend.bookstore.entity.User;
import com.backend.bookstore.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Value("${upload.directory}")
    private String uploadDirectory;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user, Long id) {

        User existingUser = getUserById(id);

        existingUser.setFirstname(user.getFirstname());
        existingUser.setLastname(user.getLastname());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());

        return userRepository.save(existingUser);

    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updatUserProfile(UserProfileDTO userProfileDTO, Long id) {

        User existingUser = getUserById(id);

        MultipartFile file = userProfileDTO.getProfileImage();
        String filename = file.getOriginalFilename();
        String filepath = uploadDirectory + File.separator + filename;

        try {

            file.transferTo(new File(filepath));

        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }

        existingUser.setProfileImage(filename);
        return userRepository.save(existingUser);
    }

    @Override
    public User updateExistingUserRole(User user, Long id) {
        
        User existingUser = getUserById(id);

        existingUser.setRole(user.getRole());

        return userRepository.save(existingUser);
    }

}
