package darkoverload.itzip.image.entity;

import darkoverload.itzip.image.domain.Image;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@Builder
@Table(name="images")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class ImageEntity {
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

    @CreatedDate
    @Column(name="create_date")
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(name="update_date")
    private LocalDateTime updateDate;

    public Image convertToDomain(){
        return Image.builder()
                .imageName(this.imageName)
                .imageType(this.imageType)
                .imagePath(this.imagePath)
                .imageSize(this.imageSize)
                .build();
    }

}

