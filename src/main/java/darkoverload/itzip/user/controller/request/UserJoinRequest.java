package darkoverload.itzip.user.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import darkoverload.itzip.user.domain.User;
import darkoverload.itzip.user.entity.Authority;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class UserJoinRequest {
    @NotBlank(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$",
            message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$",
            message = "영어, 숫자, 특수문자를 포함한 8~16자 비밀번호를 입력해주세요.")
    private String password;

    @JsonProperty("password_check")
    @NotEmpty(message = "비밀번호 확인을 입력해주세요.")
    private String passwordCheck;

    @JsonProperty("auth_code")
    @NotEmpty(message = "이메일을 인증해주세요.")
    private String authCode;

    @AssertTrue(message = "비밀번호와 비밀번호 확인이 일치하지 않습니다.")
    public boolean isPasswordMatching() {
        if (password == null || passwordCheck == null) {
            return true;
        }
        return password.equals(passwordCheck);
    }

    /**
     * entity 변환
     *
     * @return User Domain
     */
    public User toDomain() {
        return User.builder()
                .email(this.email)
                .password(this.password)
                .authority(Authority.USER)
                .build();
    }
}
