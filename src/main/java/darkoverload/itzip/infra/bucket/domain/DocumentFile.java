package darkoverload.itzip.infra.bucket.domain;

import darkoverload.itzip.feature.image.util.FileUtil;
import darkoverload.itzip.infra.bucket.util.holder.UUIDHolder;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Slf4j
@Getter
@ToString
public class DocumentFile {
    public static final String FEATURE = "resume" + File.separator;
    private static final String RESUME_FILE_PATH = File.separator + "resume";

    private final String fileName;

    private final String fileType;

    private final long size;

    private final InputStream inputStream;

    @Builder
    public DocumentFile(String fileName, String fileType, long size, InputStream inputStream) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.size = size;
        this.inputStream = inputStream;
    }

    public static DocumentFile create(UUIDHolder uuidHolder, MultipartFile multipartFile) throws IOException {
        if(!fileExtensionCheck(multipartFile.getInputStream())) {
            throw new RestApiException(CommonExceptionCode.FILE_ERROR);
        }

        return DocumentFile.builder()
                .fileName(uuidHolder.UUIDGenerate() + FileUtil.fileExtension(Objects.requireNonNull(multipartFile.getOriginalFilename())))
                .fileType(multipartFile.getContentType())
                .size(multipartFile.getSize())
                .inputStream(multipartFile.getInputStream())
                .build();
    }

    private static boolean fileExtensionCheck(InputStream inputStream) throws IOException {
        String type = FileUtil.getMimeType(inputStream);
        log.info("type :: {}", type);

        return type.equals("application/pdf") || type.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document") || type.equals("application/msword") || type.equals("text/plain");
    }

    public static String getBucketDir(String bucketName) {
        return bucketName + RESUME_FILE_PATH;
    }

    public static String getFeatureDir() {
        return FEATURE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentFile that = (DocumentFile) o;
        return size == that.size && Objects.equals(fileName, that.fileName) && Objects.equals(fileType, that.fileType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName, fileType, size);
    }

}
