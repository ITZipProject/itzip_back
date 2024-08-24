package darkoverload.itzip.feature.resume.domain.resume;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.entity.ResumeEntity;
import lombok.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateResume {

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
                .build();

    }
}
