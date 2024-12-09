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

    private String email;

    private String imageUrl;

    @Column(length=50)
    private String subject;

    @Column(length=50)
    private String phone;

    @Column(length=5000)
    private String introduction;

    @Convert(converter = StringListConverter.class)
    private List<String> links;

    @Enumerated(EnumType.STRING)
    @Column(name="public_on_off", nullable = false)
    private PublicOnOff publicOnOff;

    @Convert(converter = StringListConverter.class)
    private List<String> fileUrls;

    @Builder
    public ResumeEntity(Long id, Long userId, String email, String imageUrl, String subject, String phone, String introduction, List<String> links, PublicOnOff publicOnOff, List<String> fileUrls) {
        this.id = id;
        this.userId = userId;
        this.email = email;
        this.imageUrl = imageUrl;
        this.subject = subject;
        this.phone = phone;
        this.introduction = introduction;
        this.links = links;
        this.publicOnOff = publicOnOff;
        this.fileUrls = fileUrls;
    }

    public Resume convertToDomain(){
        return Resume.builder()
                .resumeId(this.id)
                .userId(this.userId)
                .email(this.email)
                .imageUrl(this.imageUrl)
                .subject(this.subject)
                .phone(this.phone)
                .introduction(this.introduction)
                .links(this.links)
                .publicOnOff(this.publicOnOff)
                .fileUrls(this.fileUrls)
                .build();
    }

}
