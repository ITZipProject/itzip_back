package darkoverload.itzip.feature.resume.entity;


import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.util.StringListConverter;
import darkoverload.itzip.global.entity.AuditingFields;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="resumes")
@EqualsAndHashCode(callSuper = false)
public class ResumeEntity extends AuditingFields {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id", nullable = false, updatable = false)
    private Long userId;

    @Embedded
    private ProfileInfoEntity profileInfo;

    private String imageUrl;

    @Convert(converter = StringListConverter.class)
    private List<String> links;



    @Convert(converter = StringListConverter.class)
    private List<String> fileUrls;

    @Builder
    public ResumeEntity(Long id, Long userId, String imageUrl, ProfileInfoEntity profileInfo,List<String> links, List<String> fileUrls) {
        this.id = id;
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.profileInfo = profileInfo;
        this.links = links;
        this.fileUrls = fileUrls;
    }

    public Resume convertToDomain(){
        return Resume.builder()
                .profileInfo(this.profileInfo.convertToDomain())
                .resumeId(this.id)
                .userId(this.userId)
                .imageUrl(this.imageUrl)
                .links(this.links)
                .fileUrls(this.fileUrls)
                .build();
    }

}
