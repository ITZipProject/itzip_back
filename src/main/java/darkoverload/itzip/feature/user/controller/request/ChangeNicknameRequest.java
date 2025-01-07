package darkoverload.itzip.feature.user.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 이메일 발송 dto
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeNicknameRequest {
    @NotEmpty(message = "닉네임을 입력해주세요.")
    @Pattern(regexp = "^(?=.*[가-힣a-zA-Z])[가-힣a-zA-Z0-9 _-]{2,16}$",
            message = "올바르지 않은 닉네임 형식입니다.")
    @Schema(description = "닉네임", example = "뛰어난 1번째 사자")
    private String nickname;
}
