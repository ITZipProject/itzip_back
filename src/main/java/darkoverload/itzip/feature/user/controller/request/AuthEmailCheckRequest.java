package darkoverload.itzip.feature.user.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 이메일 인증번호 체크 dto
 */
@Getter
@AllArgsConstructor
public class AuthEmailCheckRequest {
    @NotEmpty(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$",
            message = "올바르지 않은 이메일 형식입니다.")
    @Schema(description = "이메일", example = "example@gmail.com")
    private String email;

    @JsonProperty("auth_code")
    @NotEmpty(message = "인증번호를 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9]{6}$", message = "6자리의 인증번호를 입력해주세요.")
    @Schema(description = "이메일 인증번호", example = "bct76t")
    private String authCode;
}
