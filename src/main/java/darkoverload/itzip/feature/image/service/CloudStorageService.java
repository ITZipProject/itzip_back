package darkoverload.itzip.feature.image.service;

import darkoverload.itzip.feature.image.domain.Image;
import darkoverload.itzip.feature.image.util.FileUtil;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import darkoverload.itzip.infra.bucket.domain.AWSFile;
import darkoverload.itzip.infra.bucket.service.AWSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static darkoverload.itzip.feature.image.util.FileUtil.resizeImage;

@Slf4j
@Service
@RequiredArgsConstructor
public class CloudStorageService implements StorageService {

    private final ImageService imageService;
    private final AWSService awsService;


    /**
     * 임시 저장
     * @param multipartFile 파일
     * @param featureDir 저장위치 => 임시저장
     * @return image domain
     */
    @Transactional
    @Override
    public Image temporaryImageUpload(MultipartFile multipartFile, String featureDir) {
        if(multipartFile.isEmpty()) throw new RestApiException(CommonExceptionCode.IMAGE_NOT_FOUND);

        InputStream inputStream = null;

        Image result = null;
        try {
            inputStream = multipartFile.getInputStream();

            // 이미지 확장자 체크 git, jpeg, jpg, png 허용
            if(!FileUtil.imageExtensionCheck(multipartFile.getInputStream())){
                throw new RestApiException(CommonExceptionCode.IMAGE_FORMAT_ERROR);
            }

            Image originImage = Image.createImage(multipartFile, featureDir);
            AWSFile awsFile = null;
            try {
                // aws 실질적으로 upload
                awsFile = awsService.upload(originImage, inputStream);
            } catch (IOException e) {
                throw new RestApiException(CommonExceptionCode.IMAGE_ERROR);
            }

            Image insertData = Image.awsFrom(awsFile);

            // db 실질 저장
            result = imageService.save(insertData);
        } catch (IOException e) {
            throw new RestApiException(CommonExceptionCode.IMAGE_ERROR);
        }


        return result;
    }

    /**
     * 이미지 실질 저장
     * @param imagePath 이미지 경로
     * @param featureDir 저장될 폴더 이름
     * @return Image 도메인
     */
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

    @Override
    public Image imageUpload(MultipartFile multipartFile, String featureDir) {
        if(multipartFile.isEmpty()) throw new RestApiException(CommonExceptionCode.IMAGE_NOT_FOUND);

        InputStream inputStream = null;

        Image result = null;
        try {
            inputStream = multipartFile.getInputStream();

            // 이미지 확장자 체크 git, jpeg, jpg, png 허용
            if(!FileUtil.imageExtensionCheck(multipartFile.getInputStream())){
                throw new RestApiException(CommonExceptionCode.IMAGE_FORMAT_ERROR);
            }

            Image originImage = Image.createImage(multipartFile, featureDir);
            inputStream = resizeImage(originImage.getImageName(), multipartFile, 150);
            AWSFile awsFile = null;
            try {
                // aws 실질적으로 upload
                awsFile = awsService.upload(originImage, inputStream);
            } catch (IOException e) {
                throw new RestApiException(CommonExceptionCode.IMAGE_ERROR);
            }

            Image insertData = Image.awsFrom(awsFile);

            // db 실질 저장
            result = imageService.save(insertData);
        } catch (IOException e) {
            throw new RestApiException(CommonExceptionCode.IMAGE_ERROR);
        }

        return result;
    }

    /**
     * 이미지 삭제처리
     * @param imagePath 삭제될 이미지 위치
     * @param featureDir 폴더 위치
     */
    @Transactional
    public void imageDelete(String imagePath, String featureDir) {

        Image findImage = imageService.findByImagePath(imagePath);
        imageService.delete(findImage.getImageSeq());

        awsService.delete(findImage.getImageName(), featureDir);
    }
}
