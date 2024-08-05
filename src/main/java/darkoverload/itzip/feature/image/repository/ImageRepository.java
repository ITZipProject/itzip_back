package darkoverload.itzip.feature.image.repository;

import darkoverload.itzip.feature.image.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<ImageEntity, Long>, CustomImageRepository {
    Optional<ImageEntity> findByImagePath(String imagePath);

}
