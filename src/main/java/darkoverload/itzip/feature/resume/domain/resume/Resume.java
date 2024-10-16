package darkoverload.itzip.feature.resume.domain.resume;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.dto.resume.ResumeDto;
import darkoverload.itzip.feature.resume.entity.ResumeEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Resume {
    // 이메일
    private String email;

    // 핸드폰
    private String phone;

    // 제목
    private String subject;

    // 소개글
    private String introduction;

    // 공개여부
    private PublicOnOff publicOnOff;

    // 링크
    @Builder.Default
    private List<String> links = new ArrayList<>();

    // 이미지
    private String imageUrl;

    // 유저아이디
    private Long userId;

    // 이력서 아이디
    private Long resumeId;

    public static Resume create(ResumeDto resume, Long userId){
        return Resume.builder()
                .email(resume.getEmail())
                .phone(resume.getPhone())
                .subject(resume.getSubject())
                .introduction(resume.getIntroduction())
                .publicOnOff(resume.getPublicOnOff())
                .links(resume.getLinks())
                .imageUrl(resume.getImageUrl())
                .userId(userId)
                .build();
    }

    public static Resume update(ResumeDto resume, Long resumeId, Long userId) {
        return Resume.builder()
                .email(resume.getEmail())
                .phone(resume.getPhone())
                .subject(resume.getSubject())
                .introduction(resume.getIntroduction())
                .publicOnOff(resume.getPublicOnOff())
                .links(resume.getLinks())
                .imageUrl(resume.getImageUrl())
                .resumeId(resumeId)
                .userId(userId)
                .build();
    }

    public ResumeEntity toEntity() {
        return ResumeEntity.builder()
                .email(this.email)
                .phone(this.phone)
                .subject(this.subject)
                .introduction(this.introduction)
                .publicOnOff(this.publicOnOff)
                .userId(this.userId)
                .imageUrl(this.imageUrl)
                .links(this.links)
                .userId(this.userId)
                .id(this.resumeId)
                .build();

    }
}
