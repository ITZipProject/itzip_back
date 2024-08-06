package darkoverload.itzip.feature.image.entity;

import darkoverload.itzip.feature.image.domain.Image;
import darkoverload.itzip.global.entity.AuditingFields;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Builder
@Table(name="images")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class ImageEntity extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="image_seq")
    private Long imageSeq;

    @Column(name="image_name", length = 100)
    private String imageName;

    @Column(name="image_type", length = 50)
    private String imageType;

    @Column(name="image_path", length = 255)
    private String imagePath;

    @Column(name="image_size")
    private Long imageSize;

    public Image convertToDomain(){
        return Image.builder()
                .imageSeq(this.imageSeq)
                .imageName(this.imageName)
                .imageType(this.imageType)
                .imagePath(this.imagePath)
                .imageSize(this.imageSize)
                .build();
    }

}

