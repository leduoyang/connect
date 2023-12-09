package com.connect.common.util;

import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Component
public class ImageUploadUtil {
    @Value("${imageUploadUtil.profileImage.directory:./static/profileImage}")
    public String profileImageDir;

    public String profileImage(String filename, MultipartFile file) {
        try {
            Path uploadPath = Paths.get(profileImageDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(filename);
            Files.deleteIfExists(filePath);
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            throw new ConnectDataException(
                    ConnectErrorCode.INTERNAL_SERVER_ERROR,
                    "profile image upload failed :" + e.getMessage() + e.getCause()
            );
        }
        return filename;
    }

    public String getExtension(MultipartFile file) {
        if (file == null || file.getOriginalFilename() == null) {
            return null;
        }

        String filename = file.getOriginalFilename();
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
