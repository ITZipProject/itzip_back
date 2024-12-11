package darkoverload.itzip.feature.resume.dto.resume;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class ResumeDto {

    // 이메일
    @Pattern(regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$",
            message = "올바르지 않은 이메일 형식입니다.")
    private String email;

    // 핸드폰
    @Pattern(regexp= "/^(01[0-9])-?\\d{3,4}-?\\d{4}$/", message="올바르지 않은 번호입니다.")
    private String phone;

    // 제목
    @NotNull(message="제목을 입력해주세요.")
    private String subject;

    // 소개글
    @NotNull(message="소개글은 필수 입력입니다.")
    private String introduction;

    // 공개여부
    @NotNull(message="공개 여부는 필수 입력입니다.")
    private PublicOnOff publicOnOff;

    // 링크
    private List<String> links;

    // 이미지
    private String imageUrl;

    // 파일 경로
    private List<String> fileUrls;

    public ResumeDto(String email, String phone, String subject, String introduction, PublicOnOff publicOnOff, List<String> links, String imageUrl, List<String> fileUrls) {
        this.email = email;
        this.phone = phone;
        this.subject = subject;
        this.introduction = introduction;
        this.publicOnOff = publicOnOff;
        this.links = links;
        this.imageUrl = imageUrl;
        this.fileUrls = fileUrls;
    }

}
