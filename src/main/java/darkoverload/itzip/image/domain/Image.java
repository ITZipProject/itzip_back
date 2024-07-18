package darkoverload.itzip.image.domain;

import darkoverload.itzip.image.entity.ImageEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
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
}
