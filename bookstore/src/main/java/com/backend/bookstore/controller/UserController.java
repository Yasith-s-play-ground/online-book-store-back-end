package com.backend.bookstore.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.bookstore.dto.UserProfileDTO;
import com.backend.bookstore.entity.User;
import com.backend.bookstore.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {

        try {

            List<User> getUsers = userService.getAllUsers();
            return ResponseEntity.status(HttpStatus.OK).body(getUsers);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {

        try {

            User getUser = userService.getUserById(id);
            return ResponseEntity.status(HttpStatus.OK).body(getUser);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/users/update/role/{id}")
    public ResponseEntity<User> updateExistingUserRole(@RequestBody User user, @PathVariable Long id) {

        try {

            User updatedRole = userService.updateExistingUserRole(user, id);
            return ResponseEntity.status(HttpStatus.OK).body(updatedRole);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/user/update/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Long id) {

        try {

            User updateUser = userService.updateUser(user, id);
            return ResponseEntity.status(HttpStatus.OK).body(updateUser);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @PostMapping("/user/update/{id}/profileImage")
    public ResponseEntity<User> updateProfileImage(@ModelAttribute UserProfileDTO userProfileDTO, @PathVariable Long id) {

        try {

            User updateImage = userService.updatUserProfile(userProfileDTO, id);
            return ResponseEntity.status(HttpStatus.OK).body(updateImage);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        try {

            userService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.OK).body(null);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
