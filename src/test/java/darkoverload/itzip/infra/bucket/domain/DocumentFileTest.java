package darkoverload.itzip.infra.bucket.domain;

import darkoverload.itzip.infra.bucket.mock.CustomMockDocumentFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static darkoverload.itzip.infra.bucket.domain.DocumentFile.create;
import static org.assertj.core.api.Assertions.assertThat;


class DocumentFileTest {

    private CustomMockDocumentFile file;

    @BeforeEach
    void setUp() {
        byte[] content = "Custom MultipartFile Content" .getBytes();
        file = new CustomMockDocumentFile(content, "example", "example.pdf", "application/pdf");
    }

    @Test
    void create_정적_메서드_성공_테스트() throws IOException {
        DocumentFile documentFile = create(() -> "t8746312daxsz", file);

        assertThat(documentFile.getFileName()).isEqualTo("t8746312daxsz.pdf");
    }

    @Test
    void getBuckName_버킷_정보_url_가져오기_성공_테스트()  {
        assertThat(DocumentFile.getBucketDir("itziptest")).isEqualTo("itziptest/resume");
    }

    @Test
    void getFeatureDir_실제_저장_resume_디렉토리_성공_테스트() {
        assertThat(DocumentFile.getFeatureDir()).isEqualTo("resume/");
    }

}