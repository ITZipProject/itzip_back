package darkoverload.itzip.infra.bucket.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import darkoverload.itzip.feature.image.domain.Image;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import darkoverload.itzip.global.config.response.handler.Util.ExceptionHandlerUtil;
import darkoverload.itzip.infra.bucket.domain.AWSFile;
import darkoverload.itzip.infra.bucket.domain.DocumentFile;
import darkoverload.itzip.infra.bucket.domain.DocumentFiles;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static darkoverload.itzip.infra.bucket.domain.DocumentFile.getFeatureDir;

@Service
@Slf4j
@RequiredArgsConstructor
public class AWSService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${file.cloudfront-path}")
    private String filePath;

    /**
     * 버킷 업로드 코드
     *
     * @param file
     * @param inputStream
     * @return AWSFile
     * @throws IOException
     */
    public AWSFile uploadImage(Image file, InputStream inputStream) throws IOException {
        // 버킷 directory 생성
        String bucketDir = bucketName + "/" + file.getFeatureDir();
        // 이미지 bucket 저장
        amazonS3.putObject(new PutObjectRequest(bucketDir, file.getImageName(), inputStream, getObjectMetadata(file)).withCannedAcl(CannedAccessControlList.PublicRead));

        // 실질적인 bucket에서 조회되는 url 생성
        String dirUrl = filePath + file.getFeatureDir() + "/" + file.getImageName();

        log.info("imageName :: {}", file.getImageName());

        // aws 파일 형태로 반환 이유는 docs처리도 통합으로 할 예정
        return AWSFile.builder()
                .filePath(dirUrl)
                .filename(file.getImageName())
                .size(file.getImageSize())
                .fileType(file.getImageType())
                .contentType(file.getImagePath())
                .build();
    }

    /**
     * 버킷에 문서 업로드
     *
     * @param documentFiles
     * @return
     */
    public List<String> uploadDocument(DocumentFiles documentFiles) {
        String bucketDir = DocumentFile.getBucketDir(bucketName);
        List<String> documentFileList = new ArrayList<>();
        for (DocumentFile documentFile : documentFiles.getDocumentFiles()) {
            amazonS3.putObject(new PutObjectRequest(bucketDir, documentFile.getFileName(), documentFile.getInputStream(), getObjectMetadata(documentFile)).withCannedAcl(CannedAccessControlList.PublicRead));
            String fileUrl = filePath + getFeatureDir() + documentFile.getFileName();
            documentFileList.add(fileUrl);
            log.info("{}", fileUrl);
        }

        return documentFileList;
    }

    /**
     * 버킷메타데이터 생성파일
     *
     * @param image 이미지 파일
     * @return 버킷 메타데이터
     */
    private ObjectMetadata getObjectMetadata(Image image) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(image.getImageType());
        objectMetadata.setContentLength(image.getImageSize());

        return objectMetadata;
    }

    /**
     * 버킷메타데이터 생성파일
     *
     * @param image 이미지 파일
     * @return 버킷 메타데이터
     */
    private ObjectMetadata getObjectMetadata(DocumentFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getFileType());
        objectMetadata.setContentLength(file.getSize());

        return objectMetadata;
    }

    public void deleteDocumentFiles(List<String> documentUrls, String featureDir) {
        String bucketDir = bucketName + File.separator + featureDir;

        documentUrls.forEach(documentUrl -> {
            String keyName = documentUrl.substring(documentUrl.lastIndexOf("/")+1);

            log.info("documentUrl :: {} :: {}", bucketDir, keyName);
            boolean isObjectExists = amazonS3.doesObjectExist(bucketDir, keyName);
            if(isObjectExists) {
                amazonS3.deleteObject(bucketDir, keyName);
            } else {
                throw new RestApiException(CommonExceptionCode.FILE_NOT_FOUND_ERROR);
            }
        });

    }

    /**
     * 버킷삭제
     *
     * @param imageName  이미지 이름
     * @param featureDir 삭제될 폴더 위치
     */
    public void delete(String imageName, String featureDir) {
        String bucketDir = bucketName + "/" + featureDir;
        String keyName = imageName;

        log.info(bucketDir);
        // 버킷에 실제 파일 존재 여부 확인
        boolean isObjectExist = amazonS3.doesObjectExist(bucketDir, keyName);

        // 존재하면 업로드 하는 형식
        if (isObjectExist) {
            amazonS3.deleteObject(new DeleteObjectRequest(bucketDir, keyName));
        } else {
            ExceptionHandlerUtil.handleExceptionInternal(CommonExceptionCode.IMAGE_NOT_FOUND);
        }

    }

    /**
     * 실질적인 저장 디렉토리로 이동
     *
     * @param image      이미지 정보
     * @param featureDir 저장된 폴더 이름
     * @return String 실질적으로 저장된 위치 반환
     */
    public String moveFile(Image image, String featureDir) {
        String newSource = bucketName + "/" + featureDir;
        // oldResource bucketDir 현재 파일 temporary 저장위치
        String oldSource = makeOldResource(image);
        // 파일이름
        String keyName = image.getImageName();

        // 존재 여부 확인
        boolean isObjectExist = amazonS3.doesObjectExist(oldSource, image.getImageName());
        if (!isObjectExist) ExceptionHandlerUtil.handleExceptionInternal(CommonExceptionCode.IMAGE_NOT_FOUND);

        // 버킷 파일 이동
        amazonS3.copyObject(oldSource, keyName, newSource, keyName);

        // 원래 존재했던 위치 사진이나 파일 삭제
        amazonS3.deleteObject(oldSource, image.getImageName());

        return filePath + featureDir + "/" + image.getImageName();
    }

    /**
     * 현재 파일로 생성
     *
     * @param image
     * @return
     */
    private String makeOldResource(Image image) {
        if (!image.getImagePath().contains("temporary"))
            ExceptionHandlerUtil.handleExceptionInternal(CommonExceptionCode.IMAGE_NOT_TEMP);

        int index = image.getImagePath().lastIndexOf("temporary");
        String tempPath = image.getImagePath().substring(index, index + 9);

        return bucketName + "/" + tempPath;
    }
}

