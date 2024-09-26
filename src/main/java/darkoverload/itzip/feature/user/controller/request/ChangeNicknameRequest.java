package darkoverload.itzip.feature.user.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
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
    @Schema(description = "닉네임", example = "뛰어난 1번째 사자")
    private String nickname;
}
