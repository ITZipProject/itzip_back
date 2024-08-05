package darkoverload.itzip.feature.image.domain;

import darkoverload.itzip.feature.image.util.FileUtil;
import darkoverload.itzip.feature.image.entity.ImageEntity;
import darkoverload.itzip.infra.bucket.domain.AWSFile;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Image {

    private Long imageSeq;

    private String imageName;

    private String imageType;

    private Long imageSize;

    private String imagePath;

    private String featureDir;

    public static Image awsFrom(AWSFile awsFile) {
        return Image.builder()
                .imageName(awsFile.getFilename())
                .imagePath(awsFile.getFilePath())
                .imageType(awsFile.getFileType())
                .imageSize(awsFile.getSize())
                .build();
    }

    public ImageEntity convertToEntity() {
        return ImageEntity.builder()
                .imageSeq(this.imageSeq)
                .imageName(this.imageName)
                .imageType(this.imageType)
                .imagePath(this.imagePath)
                .imageSize(this.imageSize)
                .build();
    }

    public static Image createImage(MultipartFile file, String featureDir){
        return Image.builder()
                .imageSize(file.getSize())
                .imageName(FileUtil.generateFileName(file.getOriginalFilename()))
                .imageType(file.getContentType())
                .featureDir(featureDir)
                .build();
    }

}
