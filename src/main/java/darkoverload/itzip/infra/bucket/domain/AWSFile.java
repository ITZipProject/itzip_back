package darkoverload.itzip.infra.bucket.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class AWSFile {
    private String filename;

    private String contentType;

    private Long size;

    private String fileType;

    private String filePath;
}
