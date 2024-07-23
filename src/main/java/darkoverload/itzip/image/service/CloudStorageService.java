package darkoverload.itzip.image.service;

import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import darkoverload.itzip.global.config.response.handler.Util.ExceptionHandlerUtil;
import darkoverload.itzip.image.code.ImageExceptionCode;
import darkoverload.itzip.image.domain.Image;
import darkoverload.itzip.image.util.FileUtil;
import darkoverload.itzip.infra.bucket.domain.AWSFile;
import darkoverload.itzip.infra.bucket.service.AWSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class CloudStorageService implements StorageService {

    private final ImageService imageService;
    private final AWSService awsService;

    @Transactional
    @Override
    public Image temporaryImageUpload(MultipartFile multipartFile, String featureDir) {
        if(multipartFile.isEmpty()) throw new RestApiException(ImageExceptionCode.IMAGE_NOT_FOUND);

        InputStream inputStream = null;

        Image result = null;
        try {
            inputStream = multipartFile.getInputStream();
            if(!FileUtil.imageExtensionCheck(multipartFile.getInputStream())){
                throw new RestApiException(ImageExceptionCode.IMAGE_FORMAT_ERROR);
            }

            Image originImage = Image.createImage(multipartFile, featureDir);
            AWSFile awsFile = null;
            try {
                awsFile = awsService.upload(originImage, inputStream);
            } catch (IOException e) {
                throw new RestApiException(ImageExceptionCode.IMAGE_ERROR);
            }

            Image insertData = Image.builder()
                    .imageName(awsFile.getFilename())
                    .imagePath(awsFile.getFilePath())
                    .imageType(awsFile.getFileType())
                    .imageSize(awsFile.getSize())
                    .build();

            result = imageService.save(insertData);
        } catch (IOException e) {
            throw new RestApiException(ImageExceptionCode.IMAGE_ERROR);
        }


        return result;
    }

    @Transactional
    public Image imageUpload(String imagePath, String featureDir){

        Image findImage= imageService.findByImagePath(imagePath);

        String moveImagePath = awsService.moveFile(findImage, featureDir);

        imageService.imagePathUpdate(moveImagePath, findImage.getImageSeq());

        Image result = Image.builder()
                .imageSeq(findImage.getImageSeq())
                .imagePath(moveImagePath)
                .build();

        return result;
    }


    @Transactional
    public void imageDelete(String imagePath, String featureDir) {

        Image findImage = imageService.findByImagePath(imagePath);
        imageService.delete(findImage.getImageSeq());

        awsService.delete(findImage.getImageName(), featureDir);
    }
}
