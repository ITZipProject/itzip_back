package darkoverload.itzip.image.service;


import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.image.domain.Image;
import darkoverload.itzip.image.entity.ImageEntity;
import darkoverload.itzip.image.exception.CustomImageException;
import darkoverload.itzip.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{

    private final ImageRepository repository;

    @Override
    public void save(Image image) {
       repository.save(image.convertToEntity());
    }

    @Override
    public Image findByImagePath(String imagePath) {
        return repository.findByImagePath(imagePath).orElseThrow(()-> new CustomImageException(CommonExceptionCode.IMAGE_NOT_FOUND)).convertToDomain();
    }

    @Override
    public void delete(Long imageSeq) {
        repository.deleteById(imageSeq);
    }

    @Override
    public void imagePathUpdate(String imagePath, Long imageSeq) {
        repository.imagePathUpdate(imagePath, imageSeq);
    }

}
