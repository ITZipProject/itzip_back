package darkoverload.itzip.image.service;

import darkoverload.itzip.image.domain.Image;

public interface ImageService {

    void save(Image image);

    Image findByImagePath(String imagePath);

    void delete(Long imageSeq);

    void imagePathUpdate(String imagePath, Long imageSeq);
}
