package darkoverload.itzip.feature.resume.dto.resume;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "이메일", example = "user@example.com")
    private String email;

    // 핸드폰
    @Pattern(regexp= "/^(01[0-9])-?\\d{3,4}-?\\d{4}$/", message="올바르지 않은 번호입니다.")
    @Schema(description = "전화번호", example = "+821012345678")
    private String phone;

    // 제목
    @NotNull(message="제목을 입력해주세요.")
    @Schema(description = "제목", example = "Software Engineer Resume")
    private String subject;

    // 소개글
    @NotNull(message="소개글은 필수 입력입니다.")
    @Schema(description = "자기소개", example = "Experienced software engineer specializing in backend development.")
    private String introduction;

    // 공개여부
    @NotNull(message="공개 여부는 필수 입력입니다.")
    @Schema(description = "공개 여부", example = "YES")
    private PublicOnOff publicOnOff;

    // 링크
    @Schema(description = "링크 목록", example = "[\"https://github.com/user\", \"https://linkedin.com/in/user\"]")
    private List<String> links;

    // 이미지
    @Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.jpg")
    private String imageUrl;

    // 파일 경로
    @Schema(description = "파일 URL 목록", example = "[\"https://example.com\"]")
    private List<String> fileUrls;

    private int scrapCount;

    public ResumeDto(String email, String phone, String subject, String introduction, PublicOnOff publicOnOff, List<String> links, String imageUrl, List<String> fileUrls, int scrapCount) {
        this.email = email;
        this.phone = phone;
        this.subject = subject;
        this.introduction = introduction;
        this.publicOnOff = publicOnOff;
        this.links = links;
        this.imageUrl = imageUrl;
        this.fileUrls = fileUrls;
        this.scrapCount = scrapCount;
    }

}
