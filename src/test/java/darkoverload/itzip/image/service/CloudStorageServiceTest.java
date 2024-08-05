package darkoverload.itzip.image.service;



import com.amazonaws.services.s3.AmazonS3;
import darkoverload.itzip.feature.image.service.CloudStorageService;
import darkoverload.itzip.feature.image.service.ImageService;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import darkoverload.itzip.feature.image.domain.Image;
import darkoverload.itzip.feature.image.domain.ImageConst;
import darkoverload.itzip.infra.bucket.domain.AWSFile;
import darkoverload.itzip.infra.bucket.service.AWSService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import java.io.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;


@ExtendWith(MockitoExtension.class)
class CloudStorageServiceTest {

    @InjectMocks
    private CloudStorageService cloudStorageService;

    @Mock
    private ImageService imageService;

    @Mock
    private AWSService awsService;

    @Mock
    private AmazonS3 amazonS3;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
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

        given(awsService.upload(any(Image.class), any(InputStream.class))).willReturn(
                AWSFile.builder()
                        .size(image.getImageSize())
                        .filename(image.getImageName())
                        .fileType(image.getImageType())
                        .filePath("https://dy1vg9emkijkn.cloudfront.net/temporary/" + image.getImageName())
                        .build()
        );

        given(imageService.save(any(Image.class))).willReturn(image);

        // when
        Image uploadImage = cloudStorageService.temporaryImageUpload(file, ImageConst.TEMPDIR);

        // then
        assertThat(image.getImageName()).isEqualTo(uploadImage.getImageName());
        assertThat(image.getImageSize()).isEqualTo(uploadImage.getImageSize());
        assertThat(image.getImageType()).isEqualTo(uploadImage.getImageType());
    }

    @Test
    void multipartfile_null_에러_체크() throws IOException {
        // given
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "image.jpg",
                MediaType.IMAGE_PNG_VALUE,
                new byte[0]
        );

        // then
        // when
        assertThatThrownBy(()-> cloudStorageService.temporaryImageUpload(file, ImageConst.TEMPDIR)).isInstanceOf(RestApiException.class);
    }

    @Test
    void 파일_확장자_에러_체크() throws IOException {
        // given
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "image.jpg",
                MediaType.IMAGE_PNG_VALUE,
                new FileInputStream(new File("src/test/resources/image/text.txt"))
        );

        // then
        // when
        assertThatThrownBy(()-> cloudStorageService.temporaryImageUpload(file, ImageConst.TEMPDIR)).isInstanceOf(RestApiException.class);
    }

}