package darkoverload.itzip.image.repository;

import darkoverload.itzip.image.entity.ImageEntity;

import java.util.Optional;

public interface CustomImageRepository {
//    Optional<ImageEntity> findByImagePath(String imagePath);
    void imagePathUpdate(String imagePath, Long imageSeq);
}
