package darkoverload.itzip.infra.bucket.domain;

import darkoverload.itzip.infra.bucket.util.holder.UUIDHolder;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentFiles {
    private final List<DocumentFile> documentFiles;

    private DocumentFiles(List<DocumentFile> documentFiles) {
        this.documentFiles = documentFiles;
    }

    public static DocumentFiles create(UUIDHolder uuidHolder, List<MultipartFile> multipartFiles) {
        return new DocumentFiles(
                multipartFiles.stream()
                        .map(multipartFile -> {
                            try {
                                return DocumentFile.create(uuidHolder, multipartFile);
                            } catch (IOException e) {
                                throw new RestApiException(CommonExceptionCode.FILE_ERROR);
                            }
                        })
                        .collect(Collectors.toList())
        );
    }

    public List<DocumentFile> getDocumentFiles() {
        return documentFiles;
    }

}
