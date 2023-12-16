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
import java.util.List;

@Slf4j
@Component
public class ImageUploadUtil {
    @Value("${imageUploadUtil.profileImage.directory:./static/profileImage}")
    public String profileImageDir;

    @Value("#{'${imageUploadUtil.valid.extension:jpeg,jpg,png,bmp}'.split(',')}")
    private List<String> validExtension;

    @Value("${imageUploadUtil.max.upload-size:1}")
    private Integer maxUploadSize;

    public String profileImage(String filename, MultipartFile file) {
        if (!validExtension.contains(getExtension(file))) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    "invalid file extension found for profile image"
            );
        }
        if (file.getSize() > maxUploadSize * 1024 * 1024) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    "exceed max file size for profile image (should below " + maxUploadSize + "MB"
            );
        }

        try {
            Path uploadPath = Paths.get(profileImageDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(filename);
            Files.deleteIfExists(filePath);
            Files.copy(file.getInputStream(), filePath);
            return filePath.toString();
        } catch (IOException e) {
            throw new ConnectDataException(
                    ConnectErrorCode.INTERNAL_SERVER_ERROR,
                    "profile image upload failed :" + e.getMessage() + e.getCause()
            );
        }
    }

    public String getExtension(MultipartFile file) {
        if (file == null || file.getOriginalFilename() == null) {
            return null;
        }

        String filename = file.getOriginalFilename();
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
