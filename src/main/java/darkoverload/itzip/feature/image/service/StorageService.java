package darkoverload.itzip.feature.image.service;

import darkoverload.itzip.feature.image.domain.Image;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    darkoverload.itzip.feature.image.domain.Image temporaryImageUpload(MultipartFile multipartFile, String featureDir);

    Image imageUpload(String imagePath, String featureDir);

    void imageDelete(String imagePath, String featureDir);
}
