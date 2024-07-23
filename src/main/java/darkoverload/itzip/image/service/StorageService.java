package darkoverload.itzip.image.service;

import darkoverload.itzip.image.domain.Image;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;

public interface StorageService {
    Image temporaryImageUpload(MultipartFile multipartFile, String featureDir);

    Image imageUpload(String imagePath, String featureDir);

    void imageDelete(String imagePath, String featureDir);
}
