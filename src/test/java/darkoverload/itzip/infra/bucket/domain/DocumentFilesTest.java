package darkoverload.itzip.infra.bucket.domain;

import darkoverload.itzip.infra.bucket.mock.CustomMockDocumentFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DocumentFilesTest {

    private CustomMockDocumentFile fileOne;
    private CustomMockDocumentFile fileTwo;

    @BeforeEach
    void setUp() {
        byte[] content = "Custom MultipartFile Content" .getBytes();
        fileOne = new CustomMockDocumentFile(content, "example", "example.pdf", "application/pdf");
        fileTwo = new CustomMockDocumentFile(content, "example1", "example1.pdf", "application/pdf");
    }

    @Test
    void DocumentFiles_정적_메소드_팩터리_성공_테스트_코드() {
        DocumentFiles documentFiles = DocumentFiles.create(() -> "asdfffz", List.of(fileOne, fileTwo));
        InputStream inputStream = new ByteArrayInputStream("Custom MultipartFile Content" .getBytes());

        assertThat(documentFiles.getDocumentFiles()).isEqualTo(List.of(new DocumentFile("asdfffz.pdf", "application/pdf", 28L, inputStream), new DocumentFile("asdfffz.pdf", "application/pdf", 28L, inputStream)));
    }

}