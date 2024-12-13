package darkoverload.itzip.infra.bucket.domain;

import darkoverload.itzip.infra.bucket.mock.CustomMockDocumentFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static darkoverload.itzip.infra.bucket.domain.DocumentFile.create;


class DocumentFileTest {

    private CustomMockDocumentFile file;

    @BeforeEach
    void setUp() {
        byte[] content = "Custom MultipartFile Content" .getBytes();
        file = new CustomMockDocumentFile(content, "example", "example.pdf", "application/pdf");
    }

    @Test
    void create_정적_메서드_성공_테스트() throws IOException {
        create(() -> "t8746312daxsz", file);
    }


}