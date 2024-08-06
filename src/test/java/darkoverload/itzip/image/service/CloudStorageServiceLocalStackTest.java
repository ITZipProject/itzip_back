package darkoverload.itzip.image.service;

import darkoverload.itzip.feature.image.domain.Image;
import darkoverload.itzip.feature.image.domain.ImageConst;
import darkoverload.itzip.feature.image.service.CloudStorageService;
import darkoverload.itzip.feature.image.service.ImageService;
import darkoverload.itzip.global.config.aws.S3LocalStackConfig;
import darkoverload.itzip.infra.bucket.service.AWSService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Import(S3LocalStackConfig.class)
class CloudStorageServiceLocalStackTest {

    @Autowired
    private CloudStorageService cloudStorageService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private AWSService awsService;

    @BeforeEach
    public void setUp(){
        cloudStorageService = new CloudStorageService(imageService, awsService);
    }

    @Test
    void 이미지_임시_저장_성공_한다() throws IOException {
        // given
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "image.jpg",
                MediaType.IMAGE_PNG_VALUE,
                new FileInputStream(new File("src/test/resources/image/image.jpeg"))
        );

        Image image = Image.createImage(file, ImageConst.TEMPDIR);

        // when
        Image uploadImage = cloudStorageService.temporaryImageUpload(file, ImageConst.TEMPDIR);

        // then
        // assertThat(image.getImageName()).isEqualTo(uploadImage.getImageName()); -> 이미지 저장 시 UUID 방식으로 인해 파일명이 달라지는 결과 확인

        // 이미지 이름은 랜덤 값이 포함될 수 있으므로, 전체 이름이 아닌 형식만 비교
        assertThat(image.getImageName()).endsWith(".jpg");
        assertThat(image.getImageSize()).isEqualTo(uploadImage.getImageSize());
        assertThat(image.getImageType()).isEqualTo(uploadImage.getImageType());
    }

    // 추후 리사이즈 테스트 진행 예정
}