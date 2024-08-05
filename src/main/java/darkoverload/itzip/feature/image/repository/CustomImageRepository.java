package darkoverload.itzip.feature.image.repository;

public interface CustomImageRepository {
//    Optional<ImageEntity> findByImagePath(String imagePath);
    void imagePathUpdate(String imagePath, Long imageSeq);
}
