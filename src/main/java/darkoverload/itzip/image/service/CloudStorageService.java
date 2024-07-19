package darkoverload.itzip.image.service;

import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.handler.Util.ExceptionHandlerUtil;
import darkoverload.itzip.image.code.ImageExceptionCode;
import darkoverload.itzip.image.domain.Image;
import darkoverload.itzip.image.util.FileUtil;
import darkoverload.itzip.infra.bucket.domain.AWSFile;
import darkoverload.itzip.infra.bucket.service.AWSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public String temporaryImageUpload(MultipartFile multipartFile, String featureDir) {
        if(multipartFile.isEmpty()) ExceptionHandlerUtil.handleExceptionInternal(ImageExceptionCode.IMAGE_NOT_FOUND);

        InputStream inputStream = null;
        Image insertData =  null;
        try {
            String fileName = FileUtil.generateFileName(multipartFile.getOriginalFilename());
            inputStream = multipartFile.getInputStream();
            if(!FileUtil.imageExtensionCheck(multipartFile.getInputStream())){
                ExceptionHandlerUtil.handleExceptionInternal(ImageExceptionCode.IMAGE_FORMAT_ERROR);
            }

            Image originImage = Image.builder()
                    .imageSize(multipartFile.getSize())
                    .imageName(fileName)
                    .imageType(multipartFile.getContentType())
                    .featureDir(featureDir)
                    .build();

            AWSFile awsFile = null;
            try {
                awsFile = awsService.upload(originImage, inputStream);
            } catch (IOException e) {
                ExceptionHandlerUtil.handleExceptionInternal(ImageExceptionCode.IMAGE_ERROR);
            }
            insertData = Image.builder()
                    .imageName(awsFile.getFilename())
                    .imagePath(awsFile.getFilePath())
                    .imageType(awsFile.getFileType())
                    .imageSize(awsFile.getSize())
                    .build();

            imageService.save(insertData);
        } catch (IOException e) {
            ExceptionHandlerUtil.handleExceptionInternal(ImageExceptionCode.IMAGE_ERROR);
        }


        return insertData.getImagePath();
    }

    @Transactional
    public String imageUpload(String imagePath, String featureDir){

        Image findImage= imageService.findByImagePath(imagePath);

        String moveImagePath = awsService.moveFile(findImage, featureDir);

        imageService.imagePathUpdate(moveImagePath, findImage.getImageSeq());

        return moveImagePath;
    }


    @Transactional
    public void imageDelete(String imagePath, String featureDir) {

        Image findImage = imageService.findByImagePath(imagePath);
        imageService.delete(findImage.getImageSeq());

        awsService.delete(findImage.getImageName(), featureDir);

    }
}
