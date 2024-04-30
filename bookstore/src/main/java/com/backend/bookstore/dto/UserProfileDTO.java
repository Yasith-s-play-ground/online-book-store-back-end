package com.backend.bookstore.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UserProfileDTO {

    private MultipartFile profileImage;
}
