package darkoverload.itzip.feature.resume.domain.resume;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.entity.ResumeBasicInfoEntity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class ResumeBasicInfo {
    // 이메일
    private final String email;

    // 핸드폰
    private final String phone;

    // 제목
    private final String subject;

    // 소개글
    private final String introduction;

    // 공개여부
    private final PublicOnOff publicOnOff;


    @Builder
    public ResumeBasicInfo(String email, String phone, String subject, String introduction, PublicOnOff publicOnOff) {
        this.email = email;
        this.phone = phone;
        this.subject = subject;
        this.introduction = introduction;
        this.publicOnOff = publicOnOff;
    }

    public ResumeBasicInfoEntity toEntity() {
        return ResumeBasicInfoEntity.builder()
                .email(this.email)
                .introduction(this.introduction)
                .phone(this.phone)
                .subject(this.subject)
                .publicOnOff(this.publicOnOff)
                .build();
    }
}
