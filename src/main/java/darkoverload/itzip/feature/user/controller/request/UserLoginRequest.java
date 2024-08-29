package darkoverload.itzip.feature.user.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequest {
    @NotEmpty(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$",
            message = "올바르지 않은 이메일 형식입니다.")
    @Schema(description = "이메일", example = "example@gmail.com")
    private String email;

    // 영문, 특수문자, 숫자 포함 8 ~ 16자
    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$",
            message = "올바르지 않은 비밀번호 형식입니다.")
    @Schema(description = "비밀번호", example = "ExamPassword123!!")
    private String password;
}