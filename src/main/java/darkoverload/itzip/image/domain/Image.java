package darkoverload.itzip.image.domain;

import darkoverload.itzip.image.entity.ImageEntity;
import darkoverload.itzip.image.util.FileUtil;
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
