package darkoverload.itzip.image.repository;

import darkoverload.itzip.image.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, Long>, CustomImageRepository {
    
}
