package darkoverload.itzip.feature.resume.dto.resume;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.domain.resume.CreateResume;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
public class CreateResumeDto {

    // 이메일
    @Pattern(regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$",
            message = "올바르지 않은 이메일 형식입니다.")
    private String email;

    // 핸드폰
    @Pattern(regexp= "/^(01[0-9])-?\\d{3,4}-?\\d{4}$/", message="올바르지 않은 번호입니다.")
    private String phone;

    // 제목
    private String subject;

    // 소개글
    private String introduction;

    // 공개여부
    private PublicOnOff publicOnOff;

    // 링크
    private List<String> links;

    // 이미지
    private String imageUrl;

    // 유저 아이디
    private Long userId;

    public CreateResume create(Long userId) {
        return CreateResume.builder()
                .email(this.email)
                .phone(this.phone)
                .subject(this.subject)
                .introduction(this.introduction)
                .publicOnOff(this.publicOnOff)
                .links(this.links)
                .imageUrl(this.imageUrl)
                .userId(userId)
                .build();
    }
}
