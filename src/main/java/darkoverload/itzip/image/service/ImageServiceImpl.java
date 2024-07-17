package darkoverload.itzip.image.service;


import darkoverload.itzip.image.domain.Image;
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
}
