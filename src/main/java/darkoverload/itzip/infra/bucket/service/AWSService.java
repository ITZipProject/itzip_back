package darkoverload.itzip.infra.bucket.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import darkoverload.itzip.image.domain.Image;
import darkoverload.itzip.image.util.FileUtil;
import darkoverload.itzip.infra.bucket.domain.AWSFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
@Slf4j
@RequiredArgsConstructor
public class AWSService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${file.path}")
    private String filePath;

    public AWSFile upload(Object file, InputStream inputStream) throws IOException {
        log.info("== fileName ==");
        AWSFile in = null;

        // 이미지 파일 처리
        if(file instanceof Image) {
            Image image = (Image) file;
            String bucketDir = bucketName + "/" +image.getFeatureDir();
            amazonS3.putObject(new PutObjectRequest(bucketDir, image.getImageName(), inputStream, getObjectMetadata(image))
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            String dirUrl = filePath + image.getImageName();
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

}

