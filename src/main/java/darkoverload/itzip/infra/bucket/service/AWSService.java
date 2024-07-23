package darkoverload.itzip.infra.bucket.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import darkoverload.itzip.global.config.response.handler.Util.ExceptionHandlerUtil;
import darkoverload.itzip.image.code.ImageExceptionCode;
import darkoverload.itzip.image.domain.Image;
import darkoverload.itzip.infra.bucket.domain.AWSFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class AWSService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${file.cloudfront-path}")
    private String filePath;

    public AWSFile upload(Object file, InputStream inputStream) throws IOException {
        AWSFile in = null;

        // 이미지 파일 처리
        if(file instanceof Image) {
            Image image = (Image) file;
            String bucketDir = bucketName + "/" + image.getFeatureDir();
            amazonS3.putObject(new PutObjectRequest(bucketDir, image.getImageName(), inputStream, getObjectMetadata(image))
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            String dirUrl = filePath + image.getFeatureDir() + "/" + image.getImageName();

            log.info("imageName :: {}", image.getImageName());

            in = AWSFile.builder().filePath(dirUrl)
                    .filename(image.getImageName())
                    .size(image.getImageSize())
                    .fileType(image.getImageType())
                    .contentType(image.getImagePath())
                    .build();
        }

        log.info("in :: {}", in);
        return in;
    }

    private ObjectMetadata getObjectMetadata(Image image) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(image.getImageType());
        objectMetadata.setContentLength(image.getImageSize());

        return objectMetadata;
    }

    public void delete(String imageName, String featureDir){
        String bucketDir = bucketName + "/" + featureDir;
        String keyName = imageName;

        log.info(bucketDir);
        boolean isObjectExist = amazonS3.doesObjectExist(bucketDir, keyName);
        if(isObjectExist) {
            amazonS3.deleteObject(new DeleteObjectRequest(bucketDir, keyName));
        } else {
            ExceptionHandlerUtil.handleExceptionInternal(ImageExceptionCode.IMAGE_NOT_FOUND);
        }

    }

    public String moveFile(Image image,String featureDir){
        String newSource = bucketName + "/" + featureDir;
        String oldSource = makeOldResource(image);
        String keyName = image.getImageName();

        boolean isObjectExist = amazonS3.doesObjectExist(oldSource, image.getImageName());
        if(!isObjectExist) ExceptionHandlerUtil.handleExceptionInternal(ImageExceptionCode.IMAGE_NOT_FOUND);

        amazonS3.copyObject(oldSource, keyName, newSource, keyName);

        amazonS3.deleteObject(oldSource, image.getImageName());

        return filePath + featureDir + "/" + image.getImageName();
    }


    private String makeOldResource(Image image) {
        if(!image.getImagePath().contains("temporary"))
            ExceptionHandlerUtil.handleExceptionInternal(ImageExceptionCode.IMAGE_NOT_TEMP);

        int index = image.getImagePath().lastIndexOf("temporary");
        String tempPath = image.getImagePath().substring(index, index+9);

        return bucketName + "/" + tempPath;
    }
}

