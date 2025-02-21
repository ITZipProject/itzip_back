package darkoverload.itzip.feature.resume.entity.resume;


import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.entity.ResumeBasicInfoEntity;
import darkoverload.itzip.feature.resume.util.StringListConverter;
import darkoverload.itzip.global.entity.AuditingFields;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="resumes")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = false)
public class ResumeEntity extends AuditingFields {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id", nullable = false, updatable = false)
    private Long userId;

    @Embedded
    private ResumeBasicInfoEntity basicInfo;

    @Column(name="image_url")
    private String imageUrl;

    @Convert(converter = StringListConverter.class)
    private List<String> links;

    @Convert(converter = StringListConverter.class)
    @Column(name="file_urls")
    private List<String> fileUrls;

    @ColumnDefault(value = "0")
    private int scrapCount;

    @Builder
    public ResumeEntity(Long id, Long userId, String imageUrl, ResumeBasicInfoEntity basicInfo, List<String> links, List<String> fileUrls, int scrapCount) {
        this.id = id;
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.basicInfo = basicInfo;
        this.links = links;
        this.fileUrls = fileUrls;
        this.scrapCount = scrapCount;
    }

    public Resume convertToDomain(){
        return Resume.builder()
                .resumeBasicInfo(this.basicInfo.convertToDomain())
                .resumeId(this.id)
                .userId(this.userId)
                .imageUrl(this.imageUrl)
                .links(this.links)
                .fileUrls(this.fileUrls)
                .scrapCount(this.scrapCount)
                .build();
    }

}
